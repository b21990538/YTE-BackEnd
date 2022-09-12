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
import java.util.*;

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
            username = prepUsername(addUserRequest.name(), addUserRequest.surname());
        }

        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 6; i++) {   //  TODO alphanumeric generation
            int rand = (int) ((Math.random() * 25));
            char c = (char) ('a' + rand);
            password.append(c);
        }

        Optional<Authority> OptAuthority = authorityRepository.findByAuthority(addUserRequest.type().name());
        checkAuthorityExists(addUserRequest, OptAuthority);
        Authority authority = OptAuthority.get();

        List<Authority> authorityList = new ArrayList<>();
        authorityList.add(authority);

        //TODO same username conflict: append incremented number
        User savedUser = userRepository.save(new User(username, passwordEncoder.encode(password.toString()),
                authorityList, prepName(addUserRequest.name()), prepName(addUserRequest.surname()),
                addUserRequest.email()));

        if (addUserRequest.type() == AccountTypes.STUDENT) {
            savedUser.assignStudentNo();
            userRepository.save(savedUser);
        }

        return new AddUserResponse(savedUser.getUsername(), password.toString());
    }

    private static void checkAuthorityExists(AddUserRequest addUserRequest, Optional<Authority> OptAuthority) {
        if (OptAuthority.isEmpty()) {
            throw new NoSuchElementException("Authority %s not found".formatted(addUserRequest.type().name()));
        }
    }

    public static String prepName(String name) {
        StringBuilder preppedName = new StringBuilder();
        String[] splitNames = name.split("\\s+");
        for (String splitName: splitNames) {
            preppedName.append(normalizeAndCapitalize(splitName));
        }
        return preppedName.toString();
    }

    public static String prepUsername(String name, String surname) {
        String combinedName = String.join("", name.split("\\s+"))
                .toLowerCase(Locale.ROOT);
        String combinedSurname = String.join("", surname.split("\\s+"))
                .toLowerCase(Locale.ROOT);

        return (combinedName + "." + combinedSurname);
    }

    public static String normalizeAndCapitalize(String str) {
        if(str == null || str.isEmpty()) {
            return str;
        }

        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase(Locale.ROOT);
    }
}
