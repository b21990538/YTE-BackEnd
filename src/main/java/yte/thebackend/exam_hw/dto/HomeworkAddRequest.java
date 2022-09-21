package yte.thebackend.exam_hw.dto;

import yte.thebackend.common.entity.Assistant;
import yte.thebackend.course.entity.Course;
import yte.thebackend.exam_hw.entity.Homework;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
                Assistant assistant = new Assistant(assistantUsername);
                Course course = new Course(courseId);

                return new Homework(info, dueDateTime, assistant, null, course);
        }
}
