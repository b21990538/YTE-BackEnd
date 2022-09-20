package yte.thebackend.admin.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import yte.thebackend.common.entity.Authority;
import yte.thebackend.common.entity.User;

import javax.persistence.Entity;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Admin extends User {

    public Admin(String username, String password, List<Authority> authorities, String name,
                 String surname, String email) {
        super(username, password, authorities, name, surname, email);
    }

    public Admin(String username) {
        super(username);
    }
}
