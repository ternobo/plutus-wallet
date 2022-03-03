package com.ternobo.wallet.wallet.http.requests;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class ChargeWalletRequest {

    @NotNull
    private UUID token;

    @NotNull
    @Min(10)
    private int amount;

}
