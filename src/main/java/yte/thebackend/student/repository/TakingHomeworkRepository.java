package yte.thebackend.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yte.thebackend.student.entity.TakingHomework;

public interface TakingHomeworkRepository extends JpaRepository<TakingHomework, Long> {
}
