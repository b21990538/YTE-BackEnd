package yte.thebackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import yte.thebackend.entity.AuthUser;
import yte.thebackend.entity.Authority;
import yte.thebackend.entity.User;
import yte.thebackend.pojo.AddUserRequest;
import yte.thebackend.pojo.LoginRequest;
import yte.thebackend.repository.AuthorityRepository;
import yte.thebackend.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddUserService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    private final PasswordEncoder passwordEncoder;

    public LoginRequest addUser(AddUserRequest addUserRequest) {
        String username = addUserRequest.name() + "." + addUserRequest.surname(); // TODO student id?
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 6; i++) {   //  TODO alphanumeric generation
            int rand = (int) ((Math.random() * 25));
            char c = (char) ('a' + rand);
            password.append(c);
        }

        Authority authority = authorityRepository.findByAuthority(addUserRequest.type().name());

        User user = new User(addUserRequest.name(), addUserRequest.surname(), addUserRequest.email(),
                new AuthUser(username, passwordEncoder.encode(password.toString()),
                        List.of(authority)));

        userRepository.save(user);

        return new LoginRequest(username, password.toString());
    }
}
