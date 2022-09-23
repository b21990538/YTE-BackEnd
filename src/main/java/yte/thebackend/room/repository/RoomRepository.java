package yte.thebackend.room.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import yte.thebackend.room.entity.Room;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> findByName(String name);

    List<Room> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
