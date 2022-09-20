package yte.thebackend.course.dto;

import yte.thebackend.common.entity.Lecturer;
import yte.thebackend.common.entity.User;
import yte.thebackend.course.entity.Course;
import yte.thebackend.course.entity.Room;
import yte.thebackend.course.entity.TimeSlot;
import yte.thebackend.course.enums.CourseType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public record CourseAddRequest(
        @NotBlank(message = "Course name cannot be empty.")
        @Size(max = 255, message = "Name too long.")
        String name,
        @NotBlank(message = "Course description cannot be empty.")
        @Size(max = 1024, message = "Description too long.")
        String description,
        CourseType type,
        @NotBlank(message = "Course code cannot be empty.")
        @Size(max = 255, message = "Code too long.")
        String code,
        @Size(min = 1, message = "Course has no time slot allotted.")
        List<TimeSlotDTO> timeSlots,
        @NotBlank(message = "Room name cannot be empty.")
        @Size(max = 255, message = "Room name too long.")
        String room,
        @NotBlank(message = "Lecturer username cannot be empty.")
        @Size(max = 255, message = "Lecturer username too long.")
        String lectUsername
) {

    public Course toEntity() {
        List<TimeSlot> timeSlotList = new ArrayList<>();

        for (TimeSlotDTO timeSlot : timeSlots) {
            timeSlotList.add(timeSlot.toEntity());
        }

        return new Course(
                name,
                description,
                type,
                code,
                timeSlotList,
                new Room(room),
                new Lecturer(lectUsername)
        );
    }
}
