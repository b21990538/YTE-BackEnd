package yte.thebackend.course.entity;

import yte.thebackend.common.entity.BaseEntity;
import yte.thebackend.common.entity.User;
import yte.thebackend.course.enums.CourseType;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name = "course", schema = "public")
public class Course extends BaseEntity {

    private String name;
    private String description;
    private CourseType type;
    private String code;

    @Max(78)
    @Min(11)    // ex: 13-> 1:monday, 3:3rd slot of day 10.40-11.30
    private Integer dayNTime;   // TODO multiple slots

    @OneToOne
    @JoinColumn(name = "room_ID")
    private Room room;
    @ManyToOne(cascade = {CascadeType.MERGE,
            CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Integer getDayNTime() {
        return dayNTime;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
