package com.ternobo.wallet.payment.zarinpal.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.HashMap;

@Data
@Jacksonized
@Builder
public class CreatePaymentRequest {
    @JsonProperty("merchant_id")
    private String merchantId;

    private int amount;

    private String description;

    @JsonProperty("callback_url")
    private String callbackUrl;

    private String mobile;

    private String email;
}
