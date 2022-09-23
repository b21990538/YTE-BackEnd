package yte.thebackend.exam_hw.dto;

import yte.thebackend.course.entity.Course;
import yte.thebackend.room.entity.Room;
import yte.thebackend.exam_hw.entity.Exam;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public record ExamAddRequest(
        @NotBlank(message = "Exam name cannot be empty.")
        @Size(max = 255, message = "Exam name too long.")
        String name,
        @FutureOrPresent(message = "Exam date should be in the future.")
        LocalDateTime startTime,
        @Size(max = 1024, message = "Exam info too long.")
        String info,
        @NotBlank(message = "Room name cannot be empty.")
        @Size(max = 255, message = "Room name too long.")
        String roomName,
        @NotNull(message = "Course id null.")
        Long courseId
) {
    public Exam toEntity() {
        Room room = new Room(roomName);
        Course course = new Course(courseId);

        return new Exam(name, startTime, info, room, course);
    }
}
