package yte.thebackend.entity;

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
public class AuthUser implements UserDetails {

    @Id
    @Column(name = "user_id")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    private String username;
    private String password;
    private Boolean isEnabled;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE) // TODO check cascade types
    @JoinTable(name = "user_authorities", joinColumns = @JoinColumn(name = "auth_user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private List<Authority> authorities;

    public AuthUser(String username, String password, List<Authority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.isEnabled = true;
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

    public void setUser(User user) {
        this.user = user;
    }
}
