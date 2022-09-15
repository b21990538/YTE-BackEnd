package yte.thebackend.exam_hw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yte.thebackend.exam_hw.entity.Homework;

import java.util.List;

public interface HomeworkRepository extends JpaRepository<Homework, Long> {

    List<Homework> findByCourse_Id(Long courseId);
}
