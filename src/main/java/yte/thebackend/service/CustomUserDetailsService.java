package yte.thebackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import yte.thebackend.entity.AuthUser;
import yte.thebackend.entity.Authority;
import yte.thebackend.entity.User;
import yte.thebackend.repository.AuthUserRepository;
import yte.thebackend.repository.AuthorityRepository;
import yte.thebackend.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthUserRepository authUserRepository;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct  // TODO remove
    public void init() {
        authorityRepository.save(new Authority("STUDENT"));
        authorityRepository.save(new Authority("ADMIN"));
        authorityRepository.save(new Authority("ASSISTANT"));
        authorityRepository.save(new Authority("LECTURER"));

        Authority authoritySTUDENT = authorityRepository.findByAuthority("STUDENT");
        Authority authorityADMIN = authorityRepository.findByAuthority("ADMIN");

        User user1 = new User("User", "Surname", "bla@bla.com",
                new AuthUser("user", passwordEncoder.encode("user"),
                        List.of(authoritySTUDENT)));
        User user2 = new User("Adam", "Driver", "adam@glol.com",
                new AuthUser("admin", passwordEncoder.encode("admin"),
                        List.of(authorityADMIN)));

        userRepository.save(user1);
        userRepository.save(user2);
    }

    @Override   // TODO handle exception
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return authUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username %s is not present".formatted(username)));
    }
}
