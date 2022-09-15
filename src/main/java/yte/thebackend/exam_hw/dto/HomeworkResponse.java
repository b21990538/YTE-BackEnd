package yte.thebackend.exam_hw.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import yte.thebackend.common.entity.User;
import yte.thebackend.course.dto.NameSurname;
import yte.thebackend.exam_hw.entity.Homework;

import java.time.LocalDateTime;

public record HomeworkResponse(
        Long id,
        String info,
        @JsonFormat(pattern = "dd-MM HH:mm")
        LocalDateTime dueDateTime,
        NameSurname assistant,
        String fileName
) {
    public static HomeworkResponse fromEntity(Homework homework) {
        User assistant = homework.getAssistant();
        NameSurname assistantName = new NameSurname(assistant.getName(), assistant.getSurname(), assistant.getId());
        return new HomeworkResponse(homework.getId(), homework.getInfo(), homework.getDueDateTime(),
                assistantName, homework.getFile().getName());
    }
}
