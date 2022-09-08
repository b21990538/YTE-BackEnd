package yte.thebackend.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yte.thebackend.course.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
