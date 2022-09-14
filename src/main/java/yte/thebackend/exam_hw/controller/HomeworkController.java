package yte.thebackend.exam_hw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yte.thebackend.common.entity.User;
import yte.thebackend.common.response.MessageResponse;
import yte.thebackend.exam_hw.dto.ExamAddRequest;
import yte.thebackend.exam_hw.dto.HomeworkAddRequest;
import yte.thebackend.exam_hw.service.HomeworkService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hw")
public class HomeworkController {

    private final HomeworkService homeworkService;

    @PreAuthorize("hasAnyAuthority('LECTURER', 'ASSISTANT')")
    @PostMapping(consumes = "multipart/form-data")
    public MessageResponse addHomework(@RequestPart("homework") @Valid HomeworkAddRequest homeworkAddRequest,
                                       @RequestPart("file") @Valid @NotNull MultipartFile multipartFile,
                                       Authentication authentication) {
        return homeworkService.addHomework(homeworkAddRequest.toEntity(), multipartFile,
                (User) authentication.getPrincipal());
    }
}
