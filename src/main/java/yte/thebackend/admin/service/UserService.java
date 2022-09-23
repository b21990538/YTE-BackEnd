package yte.thebackend.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import yte.thebackend.admin.dto.AddUserResponse;
import yte.thebackend.admin.entity.Admin;
import yte.thebackend.common.entity.Assistant;
import yte.thebackend.common.entity.Lecturer;
import yte.thebackend.common.entity.User;
import yte.thebackend.common.entity.Authority;
import yte.thebackend.admin.dto.AddUserRequest;
import yte.thebackend.common.enums.AccountTypes;
import yte.thebackend.common.repository.UserRepository;
import yte.thebackend.common.repository.AuthorityRepository;
import yte.thebackend.common.response.MessageResponse;
import yte.thebackend.common.response.ResultType;
import yte.thebackend.student.entity.Student;

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

        Authority authority = getAuthorityByName(addUserRequest.type().name());

        List<Authority> authorityList = new ArrayList<>();
        authorityList.add(authority);

        //TODO same username conflict: append incremented number
        User savedUser = switch (addUserRequest.type()) {
            case STUDENT -> {
                savedUser = userRepository.save(new Student(username, passwordEncoder.encode(password.toString()),
                        authorityList, prepName(addUserRequest.name()), prepName(addUserRequest.surname()),
                        addUserRequest.email()));
                savedUser.assignStudentNo();
                yield userRepository.save(savedUser);
            }
            case LECTURER -> userRepository.save(new Lecturer(username, passwordEncoder.encode(password.toString()),
                    authorityList, prepName(addUserRequest.name()), prepName(addUserRequest.surname()),
                    addUserRequest.email()));
            case ASSISTANT -> userRepository.save(new Assistant(username, passwordEncoder.encode(password.toString()),
                    authorityList, prepName(addUserRequest.name()), prepName(addUserRequest.surname()),
                    addUserRequest.email()));
            case ADMIN -> userRepository.save(new Admin(username, passwordEncoder.encode(password.toString()),
                    authorityList, prepName(addUserRequest.name()), prepName(addUserRequest.surname()),
                    addUserRequest.email()));
        };

        return new AddUserResponse(savedUser.getUsername(), password.toString());
    }

    private Authority getAuthorityByName(String authorityName) {
        Optional<Authority> OptAuthority = authorityRepository.findByAuthority(authorityName);
        if (OptAuthority.isEmpty()) {
            throw new NoSuchElementException("Authority %s not found".formatted(authorityName));
        }
        return OptAuthority.get();
    }

    public static String prepName(String name) {
        StringBuilder preppedName = new StringBuilder();
        String[] splitNames = name.split("\\s+");
        for (int i = 0, splitNamesLength = splitNames.length; i < splitNamesLength; i++) {
            String splitName = splitNames[i];
            preppedName.append(normalizeAndCapitalize(splitName));
            if (i != splitNamesLength - 1) {
                preppedName.append(" ");
            }
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
        if (str == null || str.isEmpty()) {
            return str;
        }

        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase(Locale.ROOT);
    }

    @Transactional
    public MessageResponse togglePacify(Long userId) {

        User user = getUserById(userId);
        Boolean userIsActive = user.togglePacify();
        userRepository.save(user);

        if (userIsActive) {
            return new MessageResponse("User activated", ResultType.SUCCESS);
        }

        return new MessageResponse("User pacified", ResultType.SUCCESS);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User with id %d is not present".formatted(userId)));
    }
}
