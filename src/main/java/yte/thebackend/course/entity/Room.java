package yte.thebackend.course.entity;

import lombok.NoArgsConstructor;
import yte.thebackend.common.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Table(name = "room")
public class Room extends BaseEntity {

    private String name;
    private Boolean hasProjection;
    private Boolean hasComputer;
    private Boolean hasAirCond;
    private Boolean hasWindow;
    private Long capacity;

    public Room(String name) {
        this.name = name;
        this.hasProjection = false;
        this.hasComputer = false;
        this.hasAirCond = false;
        this.hasWindow = false;
        this.capacity = 50L;
    }
}
