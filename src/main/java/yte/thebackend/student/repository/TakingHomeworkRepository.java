package yte.thebackend.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yte.thebackend.student.entity.TakingHomework;

import java.util.List;
import java.util.Optional;

public interface TakingHomeworkRepository extends JpaRepository<TakingHomework, Long> {

    List<TakingHomework> findByHomework_Id(Long homeworkId);

    Optional<TakingHomework> findByStudent_IdAndHomework_Id(Long studentId, Long homeworkId);
}
