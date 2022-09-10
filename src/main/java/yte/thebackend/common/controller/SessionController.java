package yte.thebackend.common.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import yte.thebackend.common.entity.User;
import yte.thebackend.login.dto.LoginResponse;

@RestController
public class SessionController {
    @GetMapping("/sessionValid")
    public LoginResponse isSessionValid(Authentication authentication) {
        return LoginResponse.fromEntity(((User) authentication.getPrincipal()));
    }
}
