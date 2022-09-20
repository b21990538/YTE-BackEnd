package yte.thebackend.student.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import yte.thebackend.common.entity.User;
import yte.thebackend.course.entity.Course;
import yte.thebackend.exam_hw.entity.Exam;
import yte.thebackend.student.composite_key.TakingExamKey;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "taking_exam", schema = "public")
@IdClass(TakingExamKey.class)
public class TakingExam {

    @Id
    private Long studentId;

    @Id
    private Long examId;

    private Integer score;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @MapsId("examId")
    @JoinColumn(name = "exam_id")
    private Exam exam;

    public TakingExam(Long studentId, Long examId) {
        this.studentId = studentId;
        this.examId = examId;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
