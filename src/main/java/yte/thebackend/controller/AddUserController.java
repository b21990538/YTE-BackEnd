package yte.thebackend.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import yte.thebackend.pojo.AddUserRequest;
import yte.thebackend.pojo.LoginRequest;
import yte.thebackend.service.AddUserService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AddUserController {

    private final AddUserService addUserService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addUser")
    public LoginRequest addUser(@RequestBody @Valid AddUserRequest addUserRequest) {
        return addUserService.addUser(addUserRequest);
    }
}
