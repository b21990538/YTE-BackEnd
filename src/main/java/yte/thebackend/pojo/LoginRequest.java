package yte.thebackend.pojo;

import javax.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "username field cannot be empty")
        String username,
        @NotBlank(message = "password field cannot be empty")
        String password
) {
}
