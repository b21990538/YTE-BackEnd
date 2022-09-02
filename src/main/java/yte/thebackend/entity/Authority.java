package yte.thebackend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private Long id;

    // TODO database set to update
    private String authority;

    @ManyToMany(mappedBy = "authorities")
    private List<AuthUser> users;

    public Authority(String authority) {
        this.authority = authority;
    }
}
