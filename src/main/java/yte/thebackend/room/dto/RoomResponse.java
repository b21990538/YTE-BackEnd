package yte.thebackend.room.dto;

import yte.thebackend.room.entity.Room;
import java.io.Serializable;

public record RoomResponse(
        Long id,
        String name,
        Boolean hasProjection,
        Boolean hasComputer,
        Boolean hasAirCond,
        Boolean hasWindow,
        Long capacity) implements Serializable {

    public static RoomResponse fromEntity(Room room) {
        return new RoomResponse(
                room.getId(),
                room.getName(),
                room.getHasProjection(),
                room.getHasComputer(),
                room.getHasAirCond(),
                room.getHasWindow(),
                room.getCapacity()
        );
    }
}
