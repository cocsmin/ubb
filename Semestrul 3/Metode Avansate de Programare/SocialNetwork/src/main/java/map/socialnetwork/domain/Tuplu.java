package map.socialnetwork.domain;

import java.util.Objects;

public class Tuplu<E1, E2> {
    private E1 e1;
    private E2 e2;

    public Tuplu(E1 e1, E2 e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    public E1 getSt(){
        return e1;
    }

    public E2 getDr(){
        return e2;
    }

    public void setSt(E1 e1){
        this.e1 = e1;
    }

    public void setDr(E2 e2){
        this.e2 = e2;
    }

    @Override
    public String toString() {
        return "[" + e1 + ", " + e2 + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        return e1.equals(((Tuplu) o).e1) && e2.equals(((Tuplu) o).e2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(e1, e2);
    }

}
