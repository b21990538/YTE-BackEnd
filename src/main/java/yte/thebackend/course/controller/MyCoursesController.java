package yte.thebackend.course.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import yte.thebackend.common.entity.User;
import yte.thebackend.course.dto.CourseResponse;
import yte.thebackend.course.service.CourseService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MyCoursesController {

    private final CourseService courseService;

    @PreAuthorize("hasAnyAuthority('STUDENT', 'LECTURER', 'ASSISTANT')")
    @GetMapping("/my-courses")
    public List<CourseResponse> getMyCourses(Authentication authentication) {

        return courseService.getMyCourses((User) authentication.getPrincipal()).stream()
                .map(CourseResponse::fromEntity)
                .toList();
    }
}
