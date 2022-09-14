package yte.thebackend.course.composite_key;

import org.hibernate.proxy.HibernateProxy;

import java.io.Serializable;
import java.util.Objects;

public class TimeSlotKey implements Serializable {
    private Integer day;
    private Integer slot;

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
        final TimeSlotKey other = (TimeSlotKey) obj;
        if (this.day == null || this.slot == null) {
            return false;
        } else {
            return (this.day.equals(other.day) &&
                    this.slot.equals(other.slot));
        }
    }

    @Override
    public int hashCode() {
        return (Objects.hash(day) + 31*Objects.hash(slot));
    }
}
