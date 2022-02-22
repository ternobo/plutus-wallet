package com.ternobo.wallet.user.http.requests;


import javax.validation.constraints.NotBlank;

public record UserDTO(@NotBlank(message = "Name is required")
                                String name,
                                @NotBlank(message = "Username is required")
                                String username,
                                @NotBlank(message = "password is required")
                                String password,
                                @NotBlank(message = "password confirm is required")
                                String passwordConfirm) {
}
