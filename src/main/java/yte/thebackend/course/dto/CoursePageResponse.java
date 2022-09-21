package yte.thebackend.course.dto;

import yte.thebackend.common.entity.Assistant;
import yte.thebackend.course.entity.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public record CoursePageResponse(
        String name,
        String code,
        String type,
        String room,
        String description,
        String lecturerName,
        String lecturerSurname,
        List<NameSurname> assistants,
        List<TimeSlotDTO> timeSlots
) {
    public static CoursePageResponse fromEntity(Course course) {

        Set<Assistant> assistants = course.getAssistants();
        List<NameSurname> assistantNameSurnames = new ArrayList<>();

        for (Assistant assistant : assistants) {
            assistantNameSurnames.add(new NameSurname(
                    assistant.getName(),
                    assistant.getSurname(),
                    assistant.getId()
            ));
        }

        return new CoursePageResponse(
                course.getName(),
                course.getCode(),
                course.getType().name(),
                course.getRoom().getName(),
                course.getDescription(),
                course.getLecturer().getName(),
                course.getLecturer().getSurname(),
                assistantNameSurnames,
                course.getTimeSlots().stream()
                        .map(TimeSlotDTO::fromEntity).toList());
    }
}
