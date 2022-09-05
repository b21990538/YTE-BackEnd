package yte.thebackend.admin.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import yte.thebackend.admin.dto.AddUserRequest;
import yte.thebackend.admin.dto.AddUserResponse;
import yte.thebackend.admin.dto.UserResponse;
import yte.thebackend.admin.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addUser")
    public AddUserResponse addUser(@RequestBody @Valid AddUserRequest addUserRequest) {
        return userService.addUser(addUserRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getUsers")
    public List<UserResponse> getUsers() {
        return userService.getAllUsers().stream()
                .map(UserResponse::fromEntity)
                .toList();
    }
}
