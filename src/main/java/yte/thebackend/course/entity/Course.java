package yte.thebackend.course.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import yte.thebackend.common.entity.BaseEntity;
import yte.thebackend.common.entity.User;
import yte.thebackend.course.enums.CourseType;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "course", schema = "public")
public class Course extends BaseEntity {

    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private CourseType type;
    private String code;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH}) // TODO check cascade types
    @JoinTable(name = "course_times", joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = {@JoinColumn(name = "time_day"), @JoinColumn(name = "time_slot")})
    private List<TimeSlot> timeSlots;

    @OneToOne
    @JoinColumn(name = "room_ID")
    private Room room;

    @ManyToOne(cascade = {CascadeType.MERGE,
            CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "lecturer_id", nullable = false)
    private User lecturer;

    public void setLecturer(User lecturer) {
        this.lecturer = lecturer;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Course(String name, String description, CourseType type, String code, List<TimeSlot> timeSlots,
                  Room room, User lecturer) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.code = code;
        this.timeSlots = timeSlots;
        this.room = room;
        this.lecturer = lecturer;
    }

    public Course(String name, String description, CourseType type, String code) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.code = code;
    }
}
