package com.ternobo.wallet.utils.http;

public record RestfulResponse<T>(boolean status, T data, String message) {
    public RestfulResponse(boolean status, T data) {
        this(status, data, null);
    }

    public RestfulResponse(boolean status, String message) {
        this(status, null, message);
    }

    public RestfulResponse(boolean status) {
        this(status, null, null);
    }
}