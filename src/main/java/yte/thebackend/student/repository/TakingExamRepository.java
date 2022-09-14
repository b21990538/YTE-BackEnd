package yte.thebackend.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yte.thebackend.student.entity.TakingExam;

public interface TakingExamRepository extends JpaRepository<TakingExam, Long> {
}
