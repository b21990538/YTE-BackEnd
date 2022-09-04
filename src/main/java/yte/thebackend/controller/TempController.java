package yte.thebackend.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import yte.thebackend.entity.AuthUser;

import java.security.Principal;

@RestController
public class TempController {

    @GetMapping("/")
    public String mainPage(Authentication authentication) {
        return ((AuthUser) authentication.getPrincipal()).getUsername();
    }

    @GetMapping("/sessionValid")
    public Boolean isSessionValid() {
        return true;
    }
}
