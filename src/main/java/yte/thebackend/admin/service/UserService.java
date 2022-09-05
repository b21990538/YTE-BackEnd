package yte.thebackend.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import yte.thebackend.admin.dto.AddUserResponse;
import yte.thebackend.common.entity.User;
import yte.thebackend.common.entity.Authority;
import yte.thebackend.admin.dto.AddUserRequest;
import yte.thebackend.common.enums.AccountTypes;
import yte.thebackend.common.repository.UserRepository;
import yte.thebackend.common.repository.AuthorityRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public AddUserResponse addUser(AddUserRequest addUserRequest) {
        String username = null;
        if (addUserRequest.type() != AccountTypes.STUDENT) {
            username = addUserRequest.name() + "." + addUserRequest.surname();
        }

        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 6; i++) {   //  TODO alphanumeric generation
            int rand = (int) ((Math.random() * 25));
            char c = (char) ('a' + rand);
            password.append(c);
        }

        Authority authority = authorityRepository.findByAuthority(addUserRequest.type().name());
        List<Authority> authorityList = new ArrayList<>();
        authorityList.add(authority);

        User savedUser = userRepository.save(new User(username, passwordEncoder.encode(password.toString()),
                authorityList, addUserRequest.name(), addUserRequest.surname(), addUserRequest.email()));

        if (addUserRequest.type() == AccountTypes.STUDENT) {
            savedUser.assignStudentNo();
            userRepository.save(savedUser);
        }

        return new AddUserResponse(savedUser.getUsername(), password.toString());
    }
}
