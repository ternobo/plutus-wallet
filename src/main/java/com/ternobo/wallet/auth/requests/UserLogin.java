package com.ternobo.wallet.auth.requests;

import javax.validation.constraints.NotBlank;

public record UserLogin(@NotBlank String username, @NotBlank String password) {
}
