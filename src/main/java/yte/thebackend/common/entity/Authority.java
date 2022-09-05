package yte.thebackend.common.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Authority extends BaseEntity implements GrantedAuthority {

    // TODO database set to update
    private String authority;

    @ManyToMany(mappedBy = "authorities")
    private List<User> users;

    public Authority(String authority) {
        this.authority = authority;
    }
}
