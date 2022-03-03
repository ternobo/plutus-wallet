package com.ternobo.wallet.currency.exceptions;

import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class CurrencyNotFoundException extends ResponseStatusException {
    /**
     * Constructor with a response status.
     */
    public CurrencyNotFoundException() {
        super(BAD_REQUEST, "Currency Not Found");
    }
}
