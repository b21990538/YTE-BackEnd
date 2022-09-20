package yte.thebackend.course.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import yte.thebackend.common.entity.BaseEntity;

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
}
