package yte.thebackend.admin.dto;

import yte.thebackend.common.entity.User;

public record UserResponse(
        Long id,
        String username,
        Boolean isEnabled,
        String name,
        String surname,
        String email,
        String authority
) {
    public static UserResponse fromEntity(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.isEnabled(),
                user.getName(), user.getSurname(), user.getEmail(), user.getFirstAuthority());
    }
}
