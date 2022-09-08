package yte.thebackend.course.dto;

import yte.thebackend.course.enums.CourseType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

public record CourseAddRequest(
        @NotBlank(message = "Course name cannot be empty")
        String name,
        @NotBlank(message = "Course description cannot be empty")
        String description,
        CourseType type,
        @NotBlank(message = "Course code cannot be empty")
        String code,
        @Size(min = 1, message = "Course has no time slot allotted")
        List<TimeSlotDTO> timeSlots,
        @NotBlank(message = "Room name cannot be empty")
        String room,
        @NotBlank(message = "Lecturer username cannot be empty")
        String lectUsername
) {
}
