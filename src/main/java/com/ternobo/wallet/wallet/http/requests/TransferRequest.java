package com.ternobo.wallet.wallet.http.requests;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
public class TransferRequest {

    @NotBlank
    @Min(1)
    private long amount;

    @NotBlank
    private UUID sourceWalletToken;

    @NotBlank
    private UUID targetWalletToken;
}
