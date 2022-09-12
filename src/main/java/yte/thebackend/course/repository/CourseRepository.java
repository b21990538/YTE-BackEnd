package yte.thebackend.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yte.thebackend.common.entity.User;
import yte.thebackend.course.entity.Course;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByLecturer_Id(Long id);

    List<Course> findByAssistantsIsContaining(User assistant);
}
