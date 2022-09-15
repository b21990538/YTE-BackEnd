package yte.thebackend.student.dto;

import yte.thebackend.common.entity.User;
import yte.thebackend.student.entity.TakingExam;

public record ExamGradeResponse(
        Long studentId,
        Long examId,
        Integer score,
        String studentName,
        String studentSurname
) {
    public static ExamGradeResponse fromEntity(TakingExam takingExam) {

        User student = takingExam.getStudent();
        return new ExamGradeResponse(takingExam.getStudentId(), takingExam.getExamId(), takingExam.getScore(),
                student.getName(), student.getSurname());
    }
}
