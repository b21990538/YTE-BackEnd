package yte.thebackend.common.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user", schema = "public")
public class User extends BaseEntity implements UserDetails {

    @Column(unique = true)
    private String username;
    private String password;
    private Boolean isEnabled;
    private String name;
    private String surname;
    private String email;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REFRESH}) // TODO check cascade types
    @JoinTable(name = "user_authorities", joinColumns = @JoinColumn(name = "auth_user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private List<Authority> authorities;

    public User(String username, String password, List<Authority> authorities, String name, String surname,
                String email) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.isEnabled = true;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public User(String username) {
        this.username = username;

    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public List<String> getStringAuthorities() {
        List<String> authListStr = new ArrayList<>();
        for (Authority authority: authorities) {
            authListStr.add(authority.getAuthority());
        }
        return authListStr;
    }

    public void assignStudentNo() {
        this.username = String.valueOf(id + 21990538);
    }

    public String getFirstAuthority() {
        if (authorities.isEmpty()) {
            return "";
        }
        return authorities.get(0).getAuthority();
    }
}
