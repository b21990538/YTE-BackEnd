package yte.thebackend.student.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import yte.thebackend.common.entity.FileEntity;
import yte.thebackend.common.entity.User;
import yte.thebackend.course.entity.Course;
import yte.thebackend.exam_hw.entity.Homework;
import yte.thebackend.student.composite_key.TakingHomeworkKey;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "taking_homework", schema = "public")
@IdClass(TakingHomeworkKey.class)
public class TakingHomework {

    @Id
    private Long studentId;

    @Id
    private Long homeworkId;

    private Integer score;

    @OneToOne
    @JoinColumn(name = "file_ID")
    private FileEntity file;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private User student;

    @ManyToOne
    @MapsId("homeworkId")
    @JoinColumn(name = "hw_id")
    private Homework homework;


    public TakingHomework(Long studentId, Long homeworkId) {
        this.studentId = studentId;
        this.homeworkId = homeworkId;
    }

    public String getFileName() {
        if (file == null) {
            return null;
        }
        return file.getName();
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void setFile(FileEntity file) {
        this.file = file;
    }
}
