package yte.thebackend.course.dto;

import yte.thebackend.common.entity.User;
import yte.thebackend.course.entity.Course;
import yte.thebackend.course.entity.Room;
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

    public Course toEntity() {
        return new Course(
                name,
                description,
                type,
                code,
                timeSlots.stream()
                        .map(TimeSlotDTO::toEntity)
                        .toList(),
                new Room(room),
                new User(lectUsername)
        );
    }
}
