package yte.thebackend.common.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import yte.thebackend.course.entity.Course;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Lecturer extends User {

    @OneToMany(mappedBy = "lecturer")
    private List<Course> courses = new ArrayList<>();

    public Lecturer(String username, String password, List<Authority> authorities, String name,
                    String surname, String email) {
        super(username, password, authorities, name, surname, email);
    }

    public Lecturer(String username) {
        super(username);
    }
}
