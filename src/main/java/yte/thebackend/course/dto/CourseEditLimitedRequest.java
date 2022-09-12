package yte.thebackend.course.dto;

import yte.thebackend.common.entity.User;
import yte.thebackend.course.entity.Course;
import yte.thebackend.course.entity.Room;
import yte.thebackend.course.entity.TimeSlot;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public record CourseEditLimitedRequest(
        @NotBlank(message = "Course description cannot be empty")
        @Size(max = 255)
        String description,

        @Size(min = 1, message = "Course has no time slot allotted")
        List<TimeSlotDTO> timeSlots,

        @NotBlank(message = "Room name cannot be empty")
        @Size(max = 255)
        String room
) {
    public Course toEntity() {
        List<TimeSlot> timeSlotList = new ArrayList<>();

        for (TimeSlotDTO timeSlot : timeSlots) {
            timeSlotList.add(timeSlot.toEntity());
        }

        return new Course(
                null,
                description,
                null,
                null,
                timeSlotList,
                new Room(room),
                null
        );
    }
}
