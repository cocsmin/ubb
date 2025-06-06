package model;


import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Entity<ID> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1234987612L;

    protected ID id;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        return getId().equals(((Entity) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Entity{" + "id=" + id + '}';
    }

}