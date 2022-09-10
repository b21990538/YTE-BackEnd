package yte.thebackend.admin.dto;

import yte.thebackend.common.enums.AccountTypes;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record AddUserRequest(
        AccountTypes type,
        @NotBlank
        @Size(max = 255)
        String name,
        @NotBlank
        @Size(max = 255)
        String surname,
        @Email
        @Size(max = 255)
        String email
) {
}
