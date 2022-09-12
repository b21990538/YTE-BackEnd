package yte.thebackend.login.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import yte.thebackend.common.entity.User;
import yte.thebackend.login.dto.LoginRequest;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;

    public User login(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.username(),
                loginRequest.password());

        Authentication authenticatedAuthentication = authenticationManager.authenticate(token);

        SecurityContext newContext = SecurityContextHolder.createEmptyContext();
        newContext.setAuthentication(authenticatedAuthentication);
        SecurityContextHolder.setContext(newContext);

        return (User) authenticatedAuthentication.getPrincipal();
    }
}
