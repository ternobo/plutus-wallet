package com.ternobo.wallet.payment.zarinpal.exceptions;

import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;
import org.springframework.web.server.ResponseStatusException;

public class CreatePaymentUrlException extends ResponseStatusException {
    public CreatePaymentUrlException(String message) {
        super(SERVICE_UNAVAILABLE,message);
    }
}
