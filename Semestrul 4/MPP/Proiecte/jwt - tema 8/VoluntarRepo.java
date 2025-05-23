package persistance.Repo;

import model.Voluntar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoluntarRepo extends JpaRepository<Voluntar, Integer> {
    Voluntar findByUsername(String username);
}
