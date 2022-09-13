package yte.thebackend.exam_hw.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yte.thebackend.common.entity.BaseEntity;
import yte.thebackend.course.entity.Course;
import yte.thebackend.course.entity.Room;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "exam", schema = "public")
public class Exam extends BaseEntity {

    private String name;
    private LocalDateTime startTime;

    @Column(length = 1024)
    private String info;

    @OneToOne
    @JoinColumn(name = "room_ID")
    private Room room;

    @OneToOne
    @JoinColumn(name = "course_ID")
    private Course course;

    public Exam(String name, LocalDateTime startTime, String info, Room room, Course course) {
        this.name = name;
        this.startTime = startTime;
        this.info = info;
        this.room = room;
        this.course = course;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
