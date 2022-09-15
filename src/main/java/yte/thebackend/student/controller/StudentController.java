package yte.thebackend.student.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yte.thebackend.common.entity.FileEntity;
import yte.thebackend.common.entity.User;
import yte.thebackend.common.response.MessageResponse;
import yte.thebackend.exam_hw.dto.HomeworkAddRequest;
import yte.thebackend.student.dto.ExamGradeResponse;
import yte.thebackend.student.dto.HomeworkGradeResponse;
import yte.thebackend.student.service.StudentService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PreAuthorize("hasAuthority('STUDENT')")
    @PostMapping("/take-course/{courseId}")
    public MessageResponse takeCourse(Authentication authentication, @PathVariable @NotNull Long courseId) {
        return studentService.takeCourse((User) authentication.getPrincipal(), courseId);
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @GetMapping("/exam-grade-student/{examId}")
    public Integer getExamGrade(Authentication authentication, @PathVariable @NotNull Long examId) {
        User student = (User) authentication.getPrincipal();
        return studentService.getExamScoreByIds(student.getId(), examId);
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @GetMapping("/hw-grade-student/{homeworkId}")
    public Integer getHomeworkGrade(Authentication authentication, @PathVariable @NotNull Long homeworkId) {
        User student = (User) authentication.getPrincipal();
        return studentService.getHomeworkScoreByIds(student.getId(), homeworkId);
    }

    @PreAuthorize("hasAnyAuthority('LECTURER', 'ASSISTANT')")
    @GetMapping("/exam-grade/{examId}")
    public List<ExamGradeResponse> getExamGrades(Authentication authentication, @PathVariable @NotNull Long examId) {
        return studentService.getExamGrades((User) authentication.getPrincipal(), examId).stream()
                .map(ExamGradeResponse::fromEntity)
                .toList();
    }

    @PreAuthorize("hasAnyAuthority('LECTURER', 'ASSISTANT')")
    @GetMapping("/hw-grade/{homeworkId}")
    public List<HomeworkGradeResponse> getHomeworkGrades(Authentication authentication,
                                                         @PathVariable @NotNull Long homeworkId) {
        return studentService.getHomeworkGrades((User) authentication.getPrincipal(), homeworkId).stream()
                .map(HomeworkGradeResponse::fromEntity)
                .toList();
    }

    @PreAuthorize("hasAnyAuthority('LECTURER', 'ASSISTANT')")
    @PutMapping("/exam-grade/{examId}/{studentId}/{score}")
    public MessageResponse updateExamGrade(Authentication authentication, @PathVariable @NotNull Long examId,
                                           @PathVariable @NotNull Long studentId,
                                           @PathVariable @NotNull Integer score) {
        return studentService.updateExamGrade((User) authentication.getPrincipal(), examId, studentId, score);
    }

    @PreAuthorize("hasAnyAuthority('LECTURER', 'ASSISTANT')")
    @PutMapping("/hw-grade/{homeworkId}/{studentId}/{score}")
    public MessageResponse updateHomeworkGrade(Authentication authentication, @PathVariable @NotNull Long homeworkId,
                                               @PathVariable @NotNull Long studentId,
                                               @PathVariable @NotNull Integer score) {
        return studentService.updateHomeworkGrade((User) authentication.getPrincipal(), homeworkId, studentId, score);
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @PostMapping(value = "/hw-student/{homeworkId}", consumes = "multipart/form-data")
    public MessageResponse addFileToHomework(@RequestPart("file") @Valid @NotNull MultipartFile multipartFile,
                                       Authentication authentication, @PathVariable @NotNull Long homeworkId) {
        return studentService.addFileToHomework(multipartFile, (User) authentication.getPrincipal(), homeworkId);
    }

}
