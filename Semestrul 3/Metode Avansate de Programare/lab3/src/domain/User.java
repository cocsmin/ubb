package domain;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Vector;

public class User extends Entity<Long> {
    private String nume;
    private String prenume;
    List<User> friends;

    public User(String nume, String prenume) {
        this.nume = nume;
        this.prenume = prenume;
        friends = new Vector<User>();
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public void addFriend(Optional<User> user) {
        friends.add(user.get());
    }

    public void removeFriend(Optional<User> user) {
        friends.remove(user.get());
    }

    public List<User> getFriends() {
        return friends;
    }

    @Override
    public String toString() {
        return "Utilizator{" + "nume= " + nume + ", prenume= " + prenume + ", friends= " + friends + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        User user = (User) o;
        return getNume().equals(user.getNume()) && getPrenume().equals(user.getPrenume()) && getFriends().equals(user.getFriends());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNume(), getPrenume(), getFriends());
    }

}
