package yte.thebackend.student.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yte.thebackend.common.entity.Assistant;
import yte.thebackend.common.entity.Lecturer;
import yte.thebackend.common.entity.User;
import yte.thebackend.common.response.MessageResponse;
import yte.thebackend.student.dto.ExamGradeResponse;
import yte.thebackend.student.dto.HomeworkGradeResponse;
import yte.thebackend.student.entity.Student;
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
        return studentService.takeCourse((Student) authentication.getPrincipal(), courseId);
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @GetMapping("/exam-grade-student/{examId}")
    public Integer getExamGrade(Authentication authentication, @PathVariable @NotNull Long examId) {
        Student student = (Student) authentication.getPrincipal();
        return studentService.getExamScoreByIds(student.getId(), examId);
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @GetMapping("/hw-grade-student/{homeworkId}")
    public Integer getHomeworkGrade(Authentication authentication, @PathVariable @NotNull Long homeworkId) {
        Student student = (Student) authentication.getPrincipal();
        return studentService.getHomeworkScoreByIds(student.getId(), homeworkId);
    }

    @PreAuthorize("hasAnyAuthority('LECTURER', 'ASSISTANT')")
    @GetMapping("/exam-grade/{examId}")
    public List<ExamGradeResponse> getExamGrades(Authentication authentication, @PathVariable @NotNull Long examId) {
        User user = (User) authentication.getPrincipal();
        if (user instanceof Lecturer) {
            return studentService.getExamGrades((Lecturer) user, examId).stream()
                    .map(ExamGradeResponse::fromEntity)
                    .toList();
        }
        return studentService.getExamGrades((Assistant) user, examId).stream()
                .map(ExamGradeResponse::fromEntity)
                .toList();
    }

    @PreAuthorize("hasAnyAuthority('LECTURER', 'ASSISTANT')")
    @GetMapping("/hw-grade/{homeworkId}")
    public List<HomeworkGradeResponse> getHomeworkGrades(Authentication authentication,
                                                         @PathVariable @NotNull Long homeworkId) {
        User user = (User) authentication.getPrincipal();
        if (user instanceof Lecturer) {
            return studentService.getHomeworkGrades((Lecturer) user, homeworkId).stream()
                    .map(HomeworkGradeResponse::fromEntity)
                    .toList();
        }
        return studentService.getHomeworkGrades((Assistant) user, homeworkId).stream()
                .map(HomeworkGradeResponse::fromEntity)
                .toList();
    }

    @PreAuthorize("hasAnyAuthority('LECTURER', 'ASSISTANT')")
    @PutMapping("/exam-grade/{examId}/{studentId}/{score}")
    public MessageResponse updateExamGrade(Authentication authentication, @PathVariable @NotNull Long examId,
                                           @PathVariable @NotNull Long studentId,
                                           @PathVariable @NotNull Integer score) {
        User user = (User) authentication.getPrincipal();
        if (user instanceof Lecturer) {
            return studentService.updateExamGrade((Lecturer) user, examId, studentId, score);
        }
        return studentService.updateExamGrade((Assistant) user, examId, studentId, score);
    }

    @PreAuthorize("hasAnyAuthority('LECTURER', 'ASSISTANT')")
    @PutMapping("/hw-grade/{homeworkId}/{studentId}/{score}")
    public MessageResponse updateHomeworkGrade(Authentication authentication, @PathVariable @NotNull Long homeworkId,
                                               @PathVariable @NotNull Long studentId,
                                               @PathVariable @NotNull Integer score) {
        User user = (User) authentication.getPrincipal();
        if (user instanceof Lecturer) {
            return studentService.updateHomeworkGrade((Lecturer) user, homeworkId, studentId, score);
        }
        return studentService.updateHomeworkGrade((Assistant) user, homeworkId, studentId, score);
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @PostMapping(value = "/hw-student/{homeworkId}", consumes = "multipart/form-data")
    public MessageResponse addFileToHomework(@RequestPart("file") @Valid @NotNull MultipartFile multipartFile,
                                       Authentication authentication, @PathVariable @NotNull Long homeworkId) {
        return studentService.addFileToHomework(multipartFile, (Student) authentication.getPrincipal(), homeworkId);
    }

}
