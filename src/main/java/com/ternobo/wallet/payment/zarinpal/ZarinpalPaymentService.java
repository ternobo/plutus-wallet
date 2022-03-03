package com.ternobo.wallet.payment.zarinpal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ternobo.wallet.payment.IRRPaymentService;
import com.ternobo.wallet.payment.records.Payment;
import com.ternobo.wallet.payment.records.PaymentAction;
import com.ternobo.wallet.payment.records.PaymentDriver;
import com.ternobo.wallet.payment.repositories.PaymentRepository;
import com.ternobo.wallet.payment.zarinpal.exceptions.CreatePaymentUrlException;
import com.ternobo.wallet.payment.zarinpal.exceptions.InvalidTransactionIDException;
import com.ternobo.wallet.payment.zarinpal.requests.CreatePaymentRequest;
import com.ternobo.wallet.payment.zarinpal.requests.VerifyPaymentRequest;
import com.ternobo.wallet.payment.zarinpal.response.CreatePaymentResponse;
import com.ternobo.wallet.payment.zarinpal.response.PaymentCommonResponse;
import com.ternobo.wallet.wallet.records.Currency;
import com.ternobo.wallet.wallet.records.Wallet;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class ZarinpalPaymentService implements IRRPaymentService {

    private final ZarinpalPaymentConfig config;
    private final OkHttpClient client;
    private final ObjectMapper mapper;
    private final PaymentRepository paymentRepository;

    @Autowired
    public ZarinpalPaymentService(ZarinpalPaymentConfig config, OkHttpClient client, ObjectMapper mapper, PaymentRepository paymentRepository) {
        this.config = config;
        this.client = client;
        this.mapper = mapper;
        this.paymentRepository = paymentRepository;
    }

    /**
     * Create Payment request and return gateway url
     *
     * @param wallet      wallet to charge
     * @param description payment description
     * @param amount      payment amount
     * @return payment gateway url
     * @throws JsonProcessingException
     */
    @Override
    public String createPayment(Wallet wallet, PaymentAction action, String description, int amount, String callback) throws JsonProcessingException {
        // Build Request Body
        String jsonBody = this.mapper.writeValueAsString(
                CreatePaymentRequest.builder()
                        .merchantId(this.config.getMerchantID())
                        .amount(amount)
                        .description(description)
                        .callbackUrl(callback)
                        .email(wallet.getUser().getEmail())
                        .mobile(wallet.getUser().getMobile())
                        .build()
        );
        RequestBody body = RequestBody.create(jsonBody, MediaType.get("application/json"));

        // Create Request
        Request request = new Request.Builder()
                .url(this.config.getRequestPaymentUrl())
                .post(body)
                .build();

        // Send Request to Zarinpal
        try (Response response = client.newCall(request).execute()) {
            if (response.code() == 200) {
                TypeReference<PaymentCommonResponse<CreatePaymentResponse>> token = new TypeReference<>() {
                };
                PaymentCommonResponse<CreatePaymentResponse> result = this.mapper.readValue(Objects.requireNonNull(response.body()).string(), token);
                Payment payment = Payment.builder()
                        .amount(amount)
                        .driver(PaymentDriver.ZARINPAL)
                        .transactionId(result.getData().getAuthority())
                        .action(action)
                        .meta(Map.of("wallet_id", wallet.getId()))
                        .currency(Currency.IRR)
                        .build();
                this.paymentRepository.save(payment);
                return this.config.getStartPaymentUrl() + result.getData().getAuthority();
            }
        } catch (Exception e) {
            throw new CreatePaymentUrlException(e.getMessage());
        }
        return null;
    }

    @Override
    public void verifyPayment(String authority, Function<Payment, Boolean> action) throws JsonProcessingException, InvalidTransactionIDException {
        Payment transaction = this.paymentRepository.findByTransactionId(authority).orElseThrow(() -> new InvalidTransactionIDException("Invalid Transaction"));
        String jsonBody = this.mapper.writeValueAsString(
                VerifyPaymentRequest.builder()
                        .merchantId(this.config.getMerchantID())
                        .amount((int) transaction.getAmount())
                        .authority(authority)
                        .build()
        );
        RequestBody body = RequestBody.create(jsonBody, MediaType.get("application/json"));
        Request request = new Request.Builder()
                .url(this.config.getVerifyPaymentUrl())
                .post(body)
                .build();
        if (action.apply(transaction)) {
            // Send Request to Zarinpal
            try (Response response = client.newCall(request).execute()) {
                if (response.code() == 200) {
                    PaymentCommonResponse<LinkedHashMap<String, Object>> result = this.mapper.readValue(Objects.requireNonNull(response.body()).string(), PaymentCommonResponse.class);
                    if (result.getData().getOrDefault("code", 101).equals(100)) {
                        transaction.setVerifyDate(Date.valueOf(LocalDate.now()));
                        transaction.setVerificationStatus(true);
                    } else {
                        throw new InvalidTransactionIDException("Invalid Transaction");
                    }
                } else {
                    throw new InvalidTransactionIDException("Invalid Transaction");
                }
            } catch (IOException | IllegalStateException e) {
                throw new CreatePaymentUrlException(e.getMessage());
            }
        } else {
            transaction.setVerifyDate(Date.valueOf(LocalDate.now()));
            transaction.setVerificationStatus(false);
        }
        this.paymentRepository.save(transaction);
    }
}
