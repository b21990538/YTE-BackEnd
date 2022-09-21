package yte.thebackend.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yte.thebackend.student.entity.TakingExam;

import java.util.List;
import java.util.Optional;

public interface TakingExamRepository extends JpaRepository<TakingExam, Long> {

    List<TakingExam> findByExam_Id(Long examId);

    Optional<TakingExam> findByStudent_IdAndExam_Id(Long studentId, Long examId);
}
