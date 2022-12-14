package yte.thebackend.exam_hw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yte.thebackend.common.entity.Assistant;
import yte.thebackend.common.entity.Lecturer;
import yte.thebackend.common.entity.User;
import yte.thebackend.common.response.MessageResponse;
import yte.thebackend.exam_hw.dto.HomeworkAddRequest;
import yte.thebackend.exam_hw.dto.HomeworkResponse;
import yte.thebackend.exam_hw.service.HomeworkService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

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
        User user = (User) authentication.getPrincipal();
        if (user instanceof Lecturer) {
            return homeworkService.addHomework(homeworkAddRequest.toEntity(), multipartFile, (Lecturer) user);
        }
        return homeworkService.addHomework(homeworkAddRequest.toEntity(), multipartFile, (Assistant) user);
    }

    @PreAuthorize("hasAnyAuthority('LECTURER', 'ASSISTANT')")
    @GetMapping("/{homeworkId}")
    public HomeworkResponse getHomework(@PathVariable @NotNull Long homeworkId) {
        return HomeworkResponse.fromEntity(homeworkService.getHomeworkById(homeworkId));
    }

    @PreAuthorize("hasAnyAuthority('LECTURER', 'ASSISTANT', 'STUDENT')")
    @GetMapping("/course/{courseId}")
    public List<HomeworkResponse> getHomeworks(@PathVariable @NotNull Long courseId) {
        return homeworkService.getHomeworks(courseId).stream()
                .map(HomeworkResponse::fromEntity)
                .toList();
    }
}
