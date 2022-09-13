package yte.thebackend.exam_hw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yte.thebackend.exam_hw.entity.Exam;

public interface ExamRepository extends JpaRepository<Exam, Long> {
}
