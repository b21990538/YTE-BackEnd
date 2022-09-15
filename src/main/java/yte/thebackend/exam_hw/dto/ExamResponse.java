package yte.thebackend.exam_hw.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import yte.thebackend.exam_hw.entity.Exam;

import java.time.LocalDateTime;

public record ExamResponse(
        Long id,
        String name,
        @JsonFormat(pattern = "dd-MM HH:mm")
        LocalDateTime startTime,
        String info,
        String roomName
) {
    public static ExamResponse fromEntity(Exam exam) {
        return new ExamResponse(exam.getId(), exam.getName(), exam.getStartTime(),
                exam.getInfo(), exam.getRoom().getName());
    }
}
