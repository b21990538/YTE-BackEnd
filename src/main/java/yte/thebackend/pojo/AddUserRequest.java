package yte.thebackend.pojo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record AddUserRequest(
        AccountTypes type,
        @NotBlank
        String name,
        @NotBlank
        String surname,
        @Email
        String email
) {
}
