package yte.thebackend.course.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import yte.thebackend.course.compositeKey.CompositeKey;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "time_slot", schema = "public")
@IdClass(CompositeKey.class)
public class TimeSlot {
    @Id
    @Column(name = "day", nullable = false)
    private Integer day;
    @Id
    @Column(name = "slot", nullable = false)
    private Integer slot;

    @ManyToMany(mappedBy = "timeSlots")
    private List<Course> courses = new ArrayList<>();

    public TimeSlot(Integer day, Integer slot) {
        this.day = day;
        this.slot = slot;
    }
}


