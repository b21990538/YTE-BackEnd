package yte.thebackend.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yte.thebackend.course.entity.TimeSlot;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Integer> {
}
