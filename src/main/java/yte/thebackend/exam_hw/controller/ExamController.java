package yte.thebackend.exam_hw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yte.thebackend.common.entity.User;
import yte.thebackend.common.response.MessageResponse;
import yte.thebackend.exam_hw.dto.ExamAddRequest;
import yte.thebackend.exam_hw.service.ExamService;

import javax.validation.Valid;

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
}
