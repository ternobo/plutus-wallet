package com.ternobo.wallet.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ternobo.wallet.payment.records.Payment;
import com.ternobo.wallet.payment.records.PaymentAction;
import com.ternobo.wallet.payment.zarinpal.exceptions.CreatePaymentUrlException;
import com.ternobo.wallet.user.records.User;
import com.ternobo.wallet.wallet.records.Wallet;

import java.util.function.Function;

public interface IRRPaymentService {
    String createPayment(Wallet user, PaymentAction action, String description, int amount, String callback) throws JsonProcessingException, CreatePaymentUrlException;
    void verifyPayment(String authority, Function<Payment, Boolean> action);
}
