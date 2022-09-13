package yte.thebackend.common.dto;

import yte.thebackend.common.entity.User;
import yte.thebackend.course.entity.Room;

public record AutocompleteItem(
        String label
) {
    public static AutocompleteItem fromEntity(User user) {
        return new AutocompleteItem(user.getUsername());
    }

    public static AutocompleteItem fromEntity(Room room) {
        return new AutocompleteItem(room.getName());
    }
}
