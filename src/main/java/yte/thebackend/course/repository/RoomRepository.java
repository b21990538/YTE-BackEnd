package yte.thebackend.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yte.thebackend.course.entity.Room;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> findByName(String name);
}
