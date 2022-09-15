package yte.thebackend.student.dto;

import yte.thebackend.common.entity.User;
import yte.thebackend.student.entity.TakingHomework;

public record HomeworkGradeResponse(
        Long studentId,
        Long homeworkId,
        Integer score,
        String studentName,
        String studentSurname,
        String fileName
) {
    public static HomeworkGradeResponse fromEntity(TakingHomework takingHomework) {
        User student = takingHomework.getStudent();
        String fileName = takingHomework.getFileName();
        return new HomeworkGradeResponse(takingHomework.getStudentId(), takingHomework.getHomeworkId(),
                takingHomework.getScore(), student.getName(), student.getSurname(), fileName);
    }
}
