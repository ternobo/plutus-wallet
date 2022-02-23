package com.ternobo.wallet.wallet.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class WalletNotFoundException extends ResponseStatusException {

    public WalletNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Wallet Not found");
    }
}
