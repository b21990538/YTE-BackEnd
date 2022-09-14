package yte.thebackend.course.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import yte.thebackend.course.composite_key.TimeSlotKey;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "time_slot", schema = "public")
@IdClass(TimeSlotKey.class)
public class TimeSlot {
    @Id
    @Column(name = "day", nullable = false)
    private Integer day;
    @Id
    @Column(name = "slot", nullable = false)
    private Integer slot;

    // TODO Add localTime start/end, use day and localtime to check conflicts

    @ManyToMany(mappedBy = "timeSlots")
    private List<Course> courses = new ArrayList<>();

    public TimeSlot(Integer day, Integer slot) {
        this.day = day;
        this.slot = slot;
    }
}


