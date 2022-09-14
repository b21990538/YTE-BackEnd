package yte.thebackend.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yte.thebackend.common.entity.User;
import yte.thebackend.course.entity.Course;
import yte.thebackend.student.entity.TakingCourse;

import java.util.List;

public interface TakingCourseRepository extends JpaRepository<TakingCourse, Long> {

    List<TakingCourse> findByStudent_Id(Long studentId);

    List<TakingCourse> findByCourse_Id(Long courseId);
}
