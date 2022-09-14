package yte.thebackend.student.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import yte.thebackend.common.entity.User;
import yte.thebackend.common.response.MessageResponse;
import yte.thebackend.student.service.StudentService;

@RestController
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PreAuthorize("hasAuthority('STUDENT')")
    @PostMapping("/take-course/{courseId}")
    public MessageResponse takeCourse(Authentication authentication, @PathVariable Long courseId) {
        return studentService.takeCourse((User) authentication.getPrincipal(), courseId);
    }
}
