package yte.thebackend.course.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record AssistantAddRequest(
        @NotBlank(message = "Assistant username cannot be empty.")
        @Size(max = 255, message = "Assistant username too long.")
        String assistantUsername,
        @NotNull(message = "Course id null.")
        Long courseId
) {
}
