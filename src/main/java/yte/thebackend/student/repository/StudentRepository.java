package yte.thebackend.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yte.thebackend.student.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
