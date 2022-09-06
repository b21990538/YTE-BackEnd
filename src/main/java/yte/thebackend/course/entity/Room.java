package yte.thebackend.course.entity;

import yte.thebackend.common.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "room")
public class Room extends BaseEntity {

    private String name;
    private Boolean hasProjection;
    private Boolean hasComputer;
    private Boolean hasAirCond;
    private Boolean hasWindow;
    private Long capacity;


}