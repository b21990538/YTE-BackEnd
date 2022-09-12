package yte.thebackend.course.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import yte.thebackend.common.entity.User;
import yte.thebackend.common.response.MessageResponse;
import yte.thebackend.course.dto.*;
import yte.thebackend.course.service.CourseService;
import yte.thebackend.course.service.MyCoursesService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MyCoursesController {

    private final MyCoursesService myCoursesService;
    private final CourseService courseService;

    @PreAuthorize("hasAnyAuthority('STUDENT', 'LECTURER', 'ASSISTANT')")
    @GetMapping("/my-courses")
    public List<CourseResponse> getMyCourses(Authentication authentication) {

        return myCoursesService.getMyCourses((User) authentication.getPrincipal()).stream()
                .map(CourseResponse::fromEntity)
                .toList();
    }

    @PreAuthorize("hasAnyAuthority('LECTURER', 'ASSISTANT')")
    @PutMapping("/my-courses/{id}")
    public MessageResponse updateCourse(@RequestBody @Valid CourseEditLimitedRequest courseEditLimitedRequest,
                                        @PathVariable Long id, Authentication authentication) {
        return myCoursesService.updateCourse((User) authentication.getPrincipal() ,
                id, courseEditLimitedRequest.toEntity());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'STUDENT', 'LECTURER', 'ASSISTANT')")
    @GetMapping("/course-page/{id}")
    public CoursePageResponse getCoursePage(@PathVariable Long id) {
        return CoursePageResponse.fromEntity(courseService.getCourseById(id));
    }

    @PreAuthorize("hasAuthority('LECTURER')")
    @PostMapping("/my-courses/assistant")
    public MessageResponse addAssistant(Authentication authentication,
                                        @RequestBody AssistantAddRequest assistantAddRequest) {
        return myCoursesService.addAssistant((User) authentication.getPrincipal(), assistantAddRequest.courseId(),
                assistantAddRequest.assistantUsername());
    }
}
