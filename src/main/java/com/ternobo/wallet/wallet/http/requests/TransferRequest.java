package com.ternobo.wallet.wallet.http.requests;

import com.ternobo.wallet.validation.notequal.FieldsNotMatch;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.UUID;

@Data
@FieldsNotMatch(
        field = "sourceWalletToken",
        fieldMatch = "targetWalletToken",
        message = "target wallet can't be same as source wallet"
)
public class TransferRequest {

    @NotNull
    @Min(1)
    private long amount;

    @NotNull
    private UUID sourceWalletToken;

    @NotNull
    private UUID targetWalletToken;

    private String description;

    @NotNull
    private Map<String, Object> meta;

}
