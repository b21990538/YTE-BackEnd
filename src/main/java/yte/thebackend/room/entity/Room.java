package yte.thebackend.room.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import yte.thebackend.common.entity.BaseEntity;
import yte.thebackend.course.entity.Course;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "room")
public class Room extends BaseEntity {
    @Column(unique = true)
    private String name;
    private Boolean hasProjection;
    private Boolean hasComputer;
    private Boolean hasAirCond;
    private Boolean hasWindow;
    private Long capacity;

    @OneToMany(mappedBy = "room")
    private Set<Course> courses = new HashSet<>();

    public Room(String name) {
        this.name = name;
        this.hasProjection = false;
        this.hasComputer = false;
        this.hasAirCond = false;
        this.hasWindow = false;
        this.capacity = 50L;
    }

    public Room(String name, Long capacity, Boolean hasProjection, Boolean hasComputer, Boolean hasAirCond, Boolean hasWindow) {
        this.name = name;
        this.hasProjection = hasProjection;
        this.hasComputer = hasComputer;
        this.hasAirCond = hasAirCond;
        this.hasWindow = hasWindow;
        this.capacity = capacity;
    }

    public void update(Room newRoom) {
        this.name = newRoom.name;
        this.hasProjection = newRoom.hasProjection;
        this.hasComputer = newRoom.hasComputer;
        this.hasAirCond = newRoom.hasAirCond;
        this.hasWindow = newRoom.hasWindow;
        this.capacity = newRoom.capacity;
    }
}
