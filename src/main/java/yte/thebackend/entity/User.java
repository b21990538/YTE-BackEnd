package yte.thebackend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user", schema = "public")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private AuthUser authUser;

    private String name;
    private String surname;
    private String email;

    public User(String name, String surname, String email, AuthUser authUser) {
        this.authUser = authUser;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.authUser.setUser(this);
    }
}
