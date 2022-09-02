package yte.thebackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import yte.thebackend.entity.AuthUser;
import yte.thebackend.entity.Authority;
import yte.thebackend.repository.AuthUserRepository;
import yte.thebackend.repository.AuthorityRepository;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthUserRepository authUserRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct  // TODO remove
    public void init() {
        authorityRepository.save(new Authority("USER"));
        authorityRepository.save(new Authority("ADMIN"));

        Authority authorityUSER = authorityRepository.findByAuthority("USER");
        Authority authorityADMIN = authorityRepository.findByAuthority("ADMIN");

        authUserRepository.save(new AuthUser(null, "user", passwordEncoder.encode("user"),
                true, List.of(authorityUSER)));
        authUserRepository.save(new AuthUser(null, "admin", passwordEncoder.encode("admin"),
                true, List.of(authorityUSER, authorityADMIN)));
    }

    @Override   // TODO handle exception
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return authUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username %s is not present".formatted(username)));
    }
}
