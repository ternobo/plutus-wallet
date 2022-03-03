package com.ternobo.wallet.payment.zarinpal.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerifyPaymentRequest {
    @JsonProperty("merchant_id")
    public String merchantId;
    public int amount;
    public String authority;
}
