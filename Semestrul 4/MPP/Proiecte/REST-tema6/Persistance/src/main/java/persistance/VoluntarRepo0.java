package persistance;

import model.Voluntar;

public interface VoluntarRepo0 extends Repository<Voluntar, Integer> {

    Voluntar Login(String username, String password);

    Voluntar findByUsername(String username);
}
