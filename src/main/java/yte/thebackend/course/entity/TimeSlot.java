package yte.thebackend.course.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "time_slot", schema = "public")
public class TimeSlot {
    //id directly represents day and time slot
    //ex: 13-> 1:monday, 3:3rd slot of day 10.40-11.30
    //11 to 58
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToMany(mappedBy = "timeSlots")
    private List<Course> courses;

    public TimeSlot(Integer timeSlot) {
        this.id = timeSlot;
    }

    public Integer getId() {
        return id;
    }


}
