package yte.thebackend.student.entity;

import lombok.NoArgsConstructor;
import yte.thebackend.common.entity.Authority;
import yte.thebackend.common.entity.User;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@NoArgsConstructor
@Entity
public class Student extends User {

    public Student(String username, String password, List<Authority> authorities, String name,
                   String surname, String email) {
        super(username, password, authorities, name, surname, email);
    }

    public Student(String username) {
        super(username);
    }
}
