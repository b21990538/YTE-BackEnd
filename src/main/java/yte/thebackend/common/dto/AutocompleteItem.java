package yte.thebackend.common.dto;

import yte.thebackend.common.entity.User;

public record AutocompleteItem(
        String label
) {
    public static AutocompleteItem fromEntity(User user) {
        return new AutocompleteItem(user.getUsername());
    }
}
