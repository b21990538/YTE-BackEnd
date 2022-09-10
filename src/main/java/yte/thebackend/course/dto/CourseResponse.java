package yte.thebackend.course.dto;

import yte.thebackend.course.entity.Course;

import java.util.List;

public record CourseResponse(
        Long id,
        String name,
        String description,
        String type,
        String code,
        List<TimeSlotDTO> timeSlots,
        String room,
        Long room_id,
        String lectUsername,
        Long lecturer_id
) {
    public static CourseResponse fromEntity(Course course) {
        return new CourseResponse(course.getId(),
                course.getName(),
                course.getDescription(),
                course.getType().name(),
                course.getCode(),
                course.getTimeSlots().stream()
                        .map(TimeSlotDTO::fromEntity).toList(),
                course.getRoom().getName(),
                course.getRoom().getId(),
                course.getLecturer().getUsername(),
                course.getLecturer().getId());
    }
}
