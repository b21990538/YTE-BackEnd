package yte.thebackend.admin.dto;

import yte.thebackend.common.enums.AccountTypes;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record AddUserRequest(
        AccountTypes type,
        @NotBlank(message = "Name cannot be empty.")
        @Size(max = 255, message = "Name too long.")
        String name,
        @NotBlank(message = "Surname cannot be empty.")
        @Size(max = 255, message = "Surname too long.")
        String surname,
        @NotBlank(message = "Email not valid.")
        @Email(message = "Email not valid.")
        @Size(max = 255, message = "Email too long.")
        String email
) {
}
