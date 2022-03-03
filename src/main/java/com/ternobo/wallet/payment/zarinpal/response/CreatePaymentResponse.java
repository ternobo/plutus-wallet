package com.ternobo.wallet.payment.zarinpal.response;

import lombok.Data;

@Data
public class CreatePaymentResponse {
    private String code;
    private String message;
    private String authority;
    private String fee_type;
    private String fee;
}
