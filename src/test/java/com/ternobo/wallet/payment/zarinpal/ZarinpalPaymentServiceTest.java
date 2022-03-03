package com.ternobo.wallet.payment.zarinpal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ternobo.wallet.UserWalletTest;
import com.ternobo.wallet.payment.records.PaymentAction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.test.util.AssertionErrors.*;


class ZarinpalPaymentServiceTest extends UserWalletTest {

    @Autowired
    private ZarinpalPaymentService service;

    @Test
    void createPayment() {
        try {
            String paymentUrl = this.service.createPayment(
                    this.firstWallet,
                    PaymentAction.CHARGE, "Payment test",
                    11000,
                    "http://localhost:8080/wallet/charge/ir-payment/callback");
            System.out.println(paymentUrl);
            assertTrue("Invalid Payment url",paymentUrl.contains("/pg/StartPay/"));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}