package map.repository.database;

import map.domain.Voluntar;
import map.repository.Repository;

public interface VoluntarRepo0 extends Repository<Voluntar, Integer> {

    Voluntar Login(String username, String password);

    Voluntar findByUsername(String username);
}
