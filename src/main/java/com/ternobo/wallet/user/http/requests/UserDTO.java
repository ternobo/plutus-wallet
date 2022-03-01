package com.ternobo.wallet.user.http.requests;


import com.ternobo.wallet.validation.fieldmatch.FieldsValueMatch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@Table(indexes = {
        @Index(name = "idx_userdto_username", columnList = "username")
})
@AllArgsConstructor
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
        private String name;

        @Pattern(message = "invalid username", regexp = "^[a-zA-Z0-9]+([a-zA-Z0-9]([_\\- ])[a-zA-Z0-9])*[a-zA-Z0-9]+$")
        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 24, message = "username must be at least 3 character")
        private String username;

        @NotBlank(message = "password is required")
        @Size(min = 8, message = "Min password length is 8")
        private String password;

        @NotBlank(message = "password confirm is required")
        private String passwordConfirm;
}
