package yte.thebackend.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yte.thebackend.course.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
