package yte.thebackend.course.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import yte.thebackend.common.response.MessageResponse;
import yte.thebackend.course.dto.CourseAddRequest;
import yte.thebackend.course.dto.CourseResponse;
import yte.thebackend.course.service.CourseService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public MessageResponse addCourse(@RequestBody @Valid CourseAddRequest courseAddRequest) {
        return courseService.addCourse(courseAddRequest.toEntity());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'STUDENT', 'LECTURER', 'ASSISTANT')")
    @GetMapping
    public List<CourseResponse> getCourses() {
        return courseService.getCourses().stream()
                .map(CourseResponse::fromEntity)
                .toList();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public MessageResponse deleteCourse(@PathVariable Long id) {
        return courseService.deleteCourse(id);
    }
}
