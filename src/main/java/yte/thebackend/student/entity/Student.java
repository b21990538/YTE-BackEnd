package yte.thebackend.student.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import yte.thebackend.common.entity.Authority;
import yte.thebackend.common.entity.User;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
public class Student extends User {

    @OneToMany(mappedBy = "student")
    private Set<TakingCourse> takenCourses;

    @OneToMany(mappedBy = "student")
    private Set<TakingExam> takenExams;

    @OneToMany(mappedBy = "student")
    private Set<TakingHomework> takenHomeworks;

    public Student(String username, String password, List<Authority> authorities, String name,
                   String surname, String email) {
        super(username, password, authorities, name, surname, email);
    }

    public Student(String username) {
        super(username);
    }
}
