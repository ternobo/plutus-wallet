package com.ternobo.wallet.user.http.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.StandardCharsets;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class InvalidClientTokenException extends HttpClientErrorException {
    public InvalidClientTokenException() {
        super("Invalid Client Token", HttpStatus.UNAUTHORIZED, "UnAuthorized", new HttpHeaders(), "".getBytes(), StandardCharsets.UTF_8);
    }
}
