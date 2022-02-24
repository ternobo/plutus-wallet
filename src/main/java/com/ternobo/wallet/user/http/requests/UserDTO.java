package com.ternobo.wallet.user.http.requests;


import com.ternobo.wallet.validation.fieldmatch.FieldsValueMatch;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "password",
                fieldMatch = "passwordConfirm",
                message = "Passwords do not match!"
        )
})
public final class UserDTO {
        @NotBlank(message = "Name is required")
        @Size(min = 3, max = 40,message = "name must be at least 3 character")
        private final String name;

        @Pattern(message = "invalid username", regexp = "^[a-zA-Z0-9]+([a-zA-Z0-9](_|-| )[a-zA-Z0-9])*[a-zA-Z0-9]+$")
        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 24, message = "username must be at least 3 character")
        private final String username;

        @NotBlank(message = "password is required")
        @Size(min = 8, message = "Min password length is 8")
        private final String password;

        @NotBlank(message = "password confirm is required")
        private final String passwordConfirm;
}
