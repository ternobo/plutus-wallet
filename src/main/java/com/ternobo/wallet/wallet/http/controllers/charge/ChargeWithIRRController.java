package com.ternobo.wallet.wallet.http.controllers.charge;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ternobo.wallet.currency.CurrencyPriceService;
import com.ternobo.wallet.payment.IRRPaymentService;
import com.ternobo.wallet.payment.records.PaymentAction;
import com.ternobo.wallet.payment.zarinpal.exceptions.InvalidTransactionIDException;
import com.ternobo.wallet.utils.http.RestfulResponse;
import com.ternobo.wallet.wallet.http.requests.ChargeWalletRequest;
import com.ternobo.wallet.wallet.service.plutus.PlutusWalletService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.ternobo.wallet.transaction.records.TransactionEvent.CHARGE;

@Controller
public class ChargeWithIRRController {

    private final IRRPaymentService paymentService;
    private final PlutusWalletService walletService;
    private final CurrencyPriceService currencyPriceService;

    public ChargeWithIRRController(IRRPaymentService paymentService, PlutusWalletService walletService, CurrencyPriceService currencyPriceService) {
        this.paymentService = paymentService;
        this.walletService = walletService;
        this.currencyPriceService = currencyPriceService;
    }

    @PostMapping("/api/v1/wallet/ir/charge")
    @ResponseBody
    public RestfulResponse<String> charge(@RequestBody @Valid ChargeWalletRequest requestBody, HttpServletRequest request) throws JsonProcessingException {
        String callbackUrl = ServletUriComponentsBuilder.fromRequest(request).replacePath("/charge/wallet/ir/callback").build().toUriString();
        String chargeWalletGateway = this.paymentService.createPayment(
                this.walletService.findWalletByToken(requestBody.getToken()),
                PaymentAction.CHARGE,
                "Charge Wallet with IRR",
                (int) this.currencyPriceService.exchangePlutusToIRR(requestBody.getAmount()),
                callbackUrl
        );
        return new RestfulResponse<>(true, chargeWalletGateway);
    }

    @GetMapping("/charge/wallet/ir/callback")
    public String callback(@RequestParam("Authority") String authority, @RequestParam("Status") String status) throws JsonProcessingException, InvalidTransactionIDException {
        if (status.equals("OK")) {
            this.paymentService.verifyPayment(authority, payment -> {
                Long walletId = Long.valueOf((Integer) payment.getMeta().get("wallet_id"));
                this.walletService.createTransaction(walletId, this.currencyPriceService.exchangeIRRToPlutus((int) payment.getAmount()), CHARGE, payment.getTransactionId());
                return true;
            });
            return "payment/payment-success";
        } else {
            return "payment/payment-fail";
        }
    }

}
