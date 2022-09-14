package yte.thebackend.student.composite_key;

import org.hibernate.proxy.HibernateProxy;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Objects;

public class TakingExamKey implements Serializable {

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "exam_id")
    private Long examId;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof HibernateProxy) {
            obj = ((HibernateProxy) obj).getHibernateLazyInitializer().getImplementation();
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TakingExamKey other = (TakingExamKey) obj;
        if (this.studentId == null || this.examId == null) {
            return false;
        } else {
            return (this.studentId.equals(other.studentId) &&
                    this.examId.equals(other.examId));
        }
    }

    @Override
    public int hashCode() {
        return (Objects.hash(studentId) + 31*Objects.hash(examId));
    }
}
