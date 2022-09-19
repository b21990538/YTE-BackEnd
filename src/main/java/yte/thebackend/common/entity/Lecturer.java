package yte.thebackend.common.entity;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@NoArgsConstructor
@Entity
public class Lecturer extends User {

    public Lecturer(String username, String password, List<Authority> authorities, String name,
                    String surname, String email) {
        super(username, password, authorities, name, surname, email);
    }

    public Lecturer(String username) {
        super(username);
    }
}
