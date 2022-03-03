package com.ternobo.wallet.payment.zarinpal.exceptions;

import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class InvalidTransactionIDException extends ResponseStatusException {
    public InvalidTransactionIDException(String message) {
        super(BAD_REQUEST,message);
    }
}
