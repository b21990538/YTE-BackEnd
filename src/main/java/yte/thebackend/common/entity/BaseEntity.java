package yte.thebackend.common.entity;

import lombok.Getter;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.*;
import java.util.Objects;

@Getter
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Version
    private Long version;

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
        final BaseEntity other = (BaseEntity) obj;
        if (this.id == null) {
            return false;
        } else {
            return this.id.equals(other.id);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
