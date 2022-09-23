package yte.thebackend.room.dto;

import yte.thebackend.room.entity.Room;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record RoomAddRequest(
        @NotBlank(message = "Room name cannot be empty.")
        @Size(max = 255, message = "Name too long.")
        String name,
        @NotNull
        Boolean hasProjection,
        @NotNull
        Boolean hasComputer,
        @NotNull
        Boolean hasAirCond,
        @NotNull
        Boolean hasWindow,
        @NotNull(message = "Capacity cannot be null.")
        @Min(value = 0, message = "Capacity cannot be lower than 0.")
        Long capacity
) {
    public Room toEntity() {
        return new Room(
                name,
                capacity,
                hasProjection,
                hasComputer,
                hasAirCond,
                hasWindow
                );
    }
}
