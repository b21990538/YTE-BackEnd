package yte.thebackend.exam_hw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import yte.thebackend.common.entity.User;
import yte.thebackend.common.response.MessageResponse;
import yte.thebackend.exam_hw.dto.ExamAddRequest;
import yte.thebackend.exam_hw.dto.ExamResponse;
import yte.thebackend.exam_hw.dto.HomeworkResponse;
import yte.thebackend.exam_hw.service.ExamService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exams")
public class ExamController {

    private final ExamService examService;

    @PreAuthorize("hasAnyAuthority('LECTURER', 'ASSISTANT')")
    @PostMapping
    public MessageResponse addExam(@RequestBody @Valid ExamAddRequest examAddRequest,
                                   Authentication authentication) {
        return examService.addExam(examAddRequest.toEntity(),
                (User) authentication.getPrincipal());
    }

    @PreAuthorize("hasAnyAuthority('LECTURER', 'ASSISTANT', 'STUDENT')")
    @GetMapping("/course/{courseId}")
    public List<ExamResponse> getExams(@PathVariable @NotNull Long courseId) {
        return examService.getExams(courseId).stream()
                .map(ExamResponse::fromEntity)
                .toList();
    }

    @PreAuthorize("hasAnyAuthority('LECTURER', 'ASSISTANT')")
    @GetMapping("/{examId}")
    public ExamResponse getExam(@PathVariable @NotNull Long examId) {
        return ExamResponse.fromEntity(examService.getExamById(examId));
    }
}
