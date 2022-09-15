package yte.thebackend.common.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import yte.thebackend.common.entity.FileEntity;
import yte.thebackend.exam_hw.service.HomeworkService;
import yte.thebackend.student.service.StudentService;

import javax.validation.constraints.NotNull;

@Controller
@RequiredArgsConstructor
public class FileController {

    private final StudentService studentService;
    private final HomeworkService homeworkService;

    @GetMapping("/hw-grade/{homeworkId}/{studentId}")
    public ResponseEntity<byte[]> getTakingHomeworkFile(@PathVariable @NotNull Long homeworkId,
                                                        @PathVariable @NotNull Long studentId) {
        FileEntity file = studentService.getTakingHomeworkFile(studentId, homeworkId);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(file.getData());
    }

    @GetMapping("/hw/{homeworkId}/file")
    public ResponseEntity<byte[]> getHomeworkFile(@PathVariable @NotNull Long homeworkId) {
        FileEntity file = homeworkService.getHomeworkFile(homeworkId);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(file.getData());
    }
}
