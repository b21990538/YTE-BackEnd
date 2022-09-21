package yte.thebackend.exam_hw.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import yte.thebackend.common.entity.Assistant;
import yte.thebackend.common.entity.BaseEntity;
import yte.thebackend.common.entity.FileEntity;
import yte.thebackend.course.entity.Course;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "homework", schema = "public")
public class Homework extends BaseEntity {

    @Column(length = 1024)
    private String info;

    private LocalDateTime dueDateTime;

    @ManyToOne
    @JoinColumn(name = "assistant_ID")
    private Assistant assistant;

    @OneToOne
    @JoinColumn(name = "file_ID")
    private FileEntity file;

    @OneToOne
    @JoinColumn(name = "course_ID")
    private Course course;

    public Homework(String info, LocalDateTime dueDateTime, Assistant assistant,
                    FileEntity file, Course course) {
        this.info = info;
        this.dueDateTime = dueDateTime;
        this.assistant = assistant;
        this.file = file;
        this.course = course;
    }

    public void setAssistant(Assistant assistant) {
        this.assistant = assistant;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setFile(FileEntity file) {
        this.file = file;
    }
}
