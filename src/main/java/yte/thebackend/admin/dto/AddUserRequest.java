package yte.thebackend.admin.dto;

import yte.thebackend.common.enums.AccountTypes;

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
