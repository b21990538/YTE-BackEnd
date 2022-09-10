package yte.thebackend.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import yte.thebackend.common.entity.User;
import yte.thebackend.common.entity.Authority;
import yte.thebackend.common.repository.UserRepository;
import yte.thebackend.common.repository.AuthorityRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

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
        Authority authorityLECTURER = authorityRepository.findByAuthority("LECTURER");
        Authority authorityASSISTANT = authorityRepository.findByAuthority("ASSISTANT");

        List<Authority> authorityList1 = new ArrayList<>();
        authorityList1.add(authoritySTUDENT);
        List<Authority> authorityList2 = new ArrayList<>();
        authorityList2.add(authorityADMIN);
        List<Authority> authorityList3 = new ArrayList<>();
        authorityList3.add(authorityLECTURER);
        List<Authority> authorityList4 = new ArrayList<>();
        authorityList4.add(authorityASSISTANT);

        userRepository.save(new User("user", passwordEncoder.encode("user"),
                authorityList1, "User", "Surname", "bla@bla.com" ));
        userRepository.save(new User("admin", passwordEncoder.encode("admin"),
                authorityList2, "Adam", "Driver", "adam@glol.com"));
        userRepository.save(new User("yusuf", passwordEncoder.encode("yusuf"),
                authorityList3, "Yusuf", "Sari", "ys@gsgs.com"));
        userRepository.save(new User("asist", passwordEncoder.encode("asist"),
                authorityList4, "Assis", "Tant", "asis@tant.com"));
    }

    @Override   // TODO handle exception
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username %s is not present".formatted(username)));
    }
}
