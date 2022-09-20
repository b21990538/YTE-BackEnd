package yte.thebackend.common.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import yte.thebackend.course.entity.Course;
import yte.thebackend.exam_hw.entity.Homework;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
public class Assistant extends User {

    @ManyToMany(mappedBy = "assistants")
    private Set<Course> courses = new HashSet<>();

    @OneToMany(mappedBy = "assistant")
    private Set<Homework> homeworks = new HashSet<>();

    public Assistant(String username, String password, List<Authority> authorities, String name,
                     String surname, String email) {
        super(username, password, authorities, name, surname, email);
    }

    public Assistant(String username) {
        super(username);
    }
}
