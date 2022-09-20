package yte.thebackend.course.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import yte.thebackend.common.entity.Assistant;
import yte.thebackend.common.entity.Lecturer;
import yte.thebackend.common.entity.User;
import yte.thebackend.common.response.MessageResponse;
import yte.thebackend.course.dto.*;
import yte.thebackend.course.service.CourseService;
import yte.thebackend.course.service.MyCoursesService;
import yte.thebackend.student.entity.Student;

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
        User user = (User) authentication.getPrincipal();
        if (user instanceof Student) {
            return myCoursesService.getStudentCourses(user.getId()).stream()
                    .map(CourseResponse::fromEntity)
                    .toList();
        }
        if (user instanceof Assistant) {
            return myCoursesService.getAssistantCourses((Assistant) user).stream()
                    .map(CourseResponse::fromEntity)
                    .toList();
        }
        return myCoursesService.getLecturerCourses(user.getId()).stream()
                .map(CourseResponse::fromEntity)
                .toList();
    }

    @PreAuthorize("hasAnyAuthority('LECTURER', 'ASSISTANT')")
    @PutMapping("/my-courses/{id}")
    public MessageResponse updateCourse(@RequestBody @Valid CourseEditLimitedRequest courseEditLimitedRequest,
                                        @PathVariable Long id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        if (user instanceof Lecturer) {
            return myCoursesService.lecturerUpdateCourse((Lecturer) user, id, courseEditLimitedRequest.toEntity());
        }
        return myCoursesService.assistantUpdateCourse((Assistant) user, id, courseEditLimitedRequest.toEntity());
    }

    @GetMapping("/course-page/{id}")    // TODO check taking course
    public CoursePageResponse getCoursePage(@PathVariable Long id) {
        return CoursePageResponse.fromEntity(courseService.getCourseById(id));
    }

    @PreAuthorize("hasAuthority('LECTURER')")
    @PostMapping("/my-courses/assistant")
    public MessageResponse assignAssistant(Authentication authentication,
                                           @RequestBody AssistantAddRequest assistantAddRequest) {
        return myCoursesService.assignAssistant((Lecturer) authentication.getPrincipal(), assistantAddRequest.courseId(),
                assistantAddRequest.assistantUsername());
    }
}
