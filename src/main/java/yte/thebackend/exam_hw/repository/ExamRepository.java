package yte.thebackend.exam_hw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yte.thebackend.exam_hw.entity.Exam;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {

    List<Exam> findByCourse_Id(Long courseId);
}
