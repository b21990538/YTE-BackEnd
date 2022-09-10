package yte.thebackend.login.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record LoginRequest(
        @NotBlank(message = "username field cannot be empty")
        @Size(max = 255)
        String username,
        @NotBlank(message = "password field cannot be empty")
        @Size(max = 255)
        String password
) {
}
