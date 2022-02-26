package com.ternobo.wallet.wallet.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TransactionException extends ResponseStatusException {
    /**
     * Constructor with a response status.
     *
     */
    public TransactionException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
