package yte.thebackend.student.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import yte.thebackend.common.entity.User;
import yte.thebackend.course.entity.Course;
import yte.thebackend.student.composite_key.TakingCourseKey;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "taking_course", schema = "public")
@IdClass(TakingCourseKey.class)
public class TakingCourse {

    @Id
    private Long studentId;

    @Id
    private Long courseId;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    private Course course;

    //TODO add total grade?

    public TakingCourse(Long studentId, Long courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }
}
