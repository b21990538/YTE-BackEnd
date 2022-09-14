package yte.thebackend.exam_hw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yte.thebackend.exam_hw.entity.Homework;

public interface HomeworkRepository extends JpaRepository<Homework, Long> {
}
