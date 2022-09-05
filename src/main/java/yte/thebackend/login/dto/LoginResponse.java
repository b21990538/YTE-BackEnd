package yte.thebackend.login.dto;

import yte.thebackend.common.entity.User;

import java.util.List;

public record LoginResponse(
        Long id,
        String username,
        List<String> authorities
) {
    public static LoginResponse fromEntity(User user) {
        return new LoginResponse(user.getId(), user.getUsername(), user.getStringAuthorities());
    }
}
