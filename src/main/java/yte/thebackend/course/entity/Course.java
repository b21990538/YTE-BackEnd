package yte.thebackend.course.entity;

import yte.thebackend.common.entity.BaseEntity;
import yte.thebackend.common.entity.User;
import yte.thebackend.course.enums.CourseTime;
import yte.thebackend.course.enums.CourseType;

import javax.persistence.*;

@Entity
@Table(name = "course", schema = "public")
public class Course extends BaseEntity {

    private String name;
    private String description;
    private CourseType type;
    private String code;
    // TODO onetomany time
    // TODO time days

    @OneToOne
    @JoinColumn(name = "room_ID")
    private Room room;
    @ManyToOne(cascade = {CascadeType.MERGE,
            CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

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