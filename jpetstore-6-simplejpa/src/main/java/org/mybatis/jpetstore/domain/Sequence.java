package org.mybatis.jpetstore.domain;

import com.google.common.base.Objects;

import javax.annotation.Nonnull;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

@Entity(name = "Sequence")
public class Sequence implements Serializable {

    private String name;
    private String nextId;

    public Sequence() {}

    public Sequence(Domain domain, Integer nextId) {

        checkNotNull(domain, "Argument[domain] must not be null");
        checkNotNull(nextId, "Argument[nextId] must not be null");
        this.name = domain.toString();
        this.nextId = nextId.toString();
    }

    @Override
    public boolean equals(final Object o) {

        if (o == this) {
            return true;
        }
        if (! (o instanceof Sequence)) {
            return false;
        }
        Sequence that = (Sequence) o;
        return Objects.equal(this.getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }

    @Override
    public String toString() {

        return Objects.toStringHelper(this)
                .add("name", name)
                .add("nextId", nextId)
                .toString();
    }

    @Id
    public String getName() {
        return name;
    }

    public void setName(@Nonnull final String name) {

        checkNotNull(name, "Argument[name] must not be null");
        this.name = name;
    }

    public String getNextId() {
        return nextId;
    }

    public void setNextId(@Nonnull final String nextId) {
        this.nextId = nextId;
    }

    @Transient
    public Integer getIntNextId() {
        return Integer.parseInt(nextId);
    }

    public void setIntNextId(@Nonnull final Integer nextId) {

        checkNotNull(nextId, "Argument[nextId] must not be null");
        this.nextId = nextId.toString();
    }

}
