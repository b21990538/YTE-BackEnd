package yte.thebackend.admin.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import yte.thebackend.admin.dto.AddUserRequest;
import yte.thebackend.admin.dto.AddUserResponse;
import yte.thebackend.admin.dto.UserResponse;
import yte.thebackend.admin.service.UserService;
import yte.thebackend.common.response.MessageResponse;
import yte.thebackend.common.response.ResultType;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.NoSuchElementException;

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

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/pacify-user/{userId}")
    public MessageResponse togglePacify(@PathVariable @NotNull Long userId) {
        return userService.togglePacify(userId);
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class, NoSuchElementException.class})
    public ResponseEntity<MessageResponse> handleValidationErrors(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new MessageResponse(exception.getMessage(), ResultType.ERROR));
    }
}
