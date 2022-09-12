package yte.thebackend.course.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record AssistantAddRequest(
        @NotBlank
        @Size(max = 255)
        String assistantUsername,
        Long courseId
) {
}
