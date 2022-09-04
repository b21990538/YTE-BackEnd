package yte.thebackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import yte.thebackend.entity.AuthUser;
import yte.thebackend.pojo.LoginRequest;
import yte.thebackend.pojo.LoginResponse;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;

    public LoginResponse login(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.username(),
                loginRequest.password());

        Authentication authenticatedAuthentication = authenticationManager.authenticate(token);

        SecurityContext newContext = SecurityContextHolder.createEmptyContext();
        newContext.setAuthentication(authenticatedAuthentication);
        SecurityContextHolder.setContext(newContext);

        AuthUser authUser = (AuthUser) authenticatedAuthentication.getPrincipal();

        return new LoginResponse(authUser.getId(),
                authUser.getUsername(), authUser.getStringAuthorities());  // TODO auth success
    }
}
