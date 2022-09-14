package yte.thebackend.exam_hw.dto;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import yte.thebackend.common.entity.FileEntity;
import yte.thebackend.common.entity.User;
import yte.thebackend.course.entity.Course;
import yte.thebackend.exam_hw.entity.Homework;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.time.LocalDateTime;

public record HomeworkAddRequest(
        @Size(max = 1024, message = "Homework info too long.")
        String info,

        @FutureOrPresent(message = "Due date should be in the future.")
        LocalDateTime dueDateTime,

        @NotBlank(message = "Assistant username cannot be empty.")
        @Size(max = 255, message = "Assistant username too long.")
        String assistantUsername,

        @NotNull(message = "Course id null.")
        Long courseId
) {
        public Homework toEntity() {
                User assistant = new User(assistantUsername);
                Course course = new Course(courseId);

                return new Homework(info, dueDateTime, assistant, null, course);
        }
}
