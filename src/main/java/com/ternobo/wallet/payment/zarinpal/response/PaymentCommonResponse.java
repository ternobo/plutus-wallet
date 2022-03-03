package com.ternobo.wallet.payment.zarinpal.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentCommonResponse<T> {
    private T data;
    private Object errors;
}
