package com.ternobo.wallet.user.http.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class UsernameDuplicatedException extends ResponseStatusException {
    public UsernameDuplicatedException() {
        super(HttpStatus.UNPROCESSABLE_ENTITY, "Username is duplicated");
    }
}
