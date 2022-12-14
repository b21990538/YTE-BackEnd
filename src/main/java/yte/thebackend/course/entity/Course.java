package yte.thebackend.course.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import yte.thebackend.common.entity.Assistant;
import yte.thebackend.common.entity.BaseEntity;
import yte.thebackend.common.entity.Lecturer;
import yte.thebackend.course.enums.CourseType;
import yte.thebackend.room.entity.Room;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "course", schema = "public")
public class Course extends BaseEntity {

    private String name;
    @Column(length = 1024)
    private String description;
    @Enumerated(EnumType.STRING)
    private CourseType type;
    private String code;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH}) // TODO check cascade types
    @JoinTable(name = "course_times", joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = {@JoinColumn(name = "time_day"), @JoinColumn(name = "time_slot")})
    private List<TimeSlot> timeSlots = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "room_ID")
    private Room room;

    @ManyToOne(cascade = {CascadeType.MERGE,
            CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "lecturer_id", nullable = false)
    private Lecturer lecturer;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private Set<Assistant> assistants = new HashSet<>();


    public void addAssistant(Assistant assistant) {
        assistants.add(assistant);
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Course(String name, String description, CourseType type, String code, List<TimeSlot> timeSlots,
                  Room room, Lecturer lecturer) {
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

    public Course(Long id) {
        this.id = id;
    }

    public void update(Course newCourse) {
        this.name = newCourse.name;
        this.description = newCourse.description;
        this.type = newCourse.type;
        this.code = newCourse.code;
        this.timeSlots = newCourse.timeSlots;
        this.room = newCourse.room;
        this.lecturer = newCourse.lecturer;
    }

    public void restrictedUpdate(Course newCourse) {
        this.description = newCourse.description;
        this.timeSlots = newCourse.timeSlots;
        this.room = newCourse.room;
    }
}
