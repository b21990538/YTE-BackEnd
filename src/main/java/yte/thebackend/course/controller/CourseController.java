package yte.thebackend.course.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import yte.thebackend.common.response.MessageResponse;
import yte.thebackend.course.dto.CourseAddRequest;
import yte.thebackend.course.service.CourseService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addCourse")
    public MessageResponse addCourse(@RequestBody @Valid CourseAddRequest courseAddRequest) {
        return courseService.addCourse(courseAddRequest);
    }
}
