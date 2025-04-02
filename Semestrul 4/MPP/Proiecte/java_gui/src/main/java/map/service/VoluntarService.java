package map.service;

import map.domain.Voluntar;
import map.repository.database.VoluntarDBRepo;
import map.repository.database.VoluntarRepo0;
import org.mindrot.jbcrypt.BCrypt;

public class VoluntarService{

    private VoluntarRepo0 voluntarRepo;

    public VoluntarService(VoluntarDBRepo voluntarRepo) {
        this.voluntarRepo = voluntarRepo;
    }

    public Voluntar login(String username, String parola) throws Exception {
        Voluntar voluntar = voluntarRepo.findByUsername(username);
        if (voluntar != null && BCrypt.checkpw(parola, voluntar.getPassword()))
            return voluntar;
        throw new Exception("Date de autentificare incorecte!");
    }

}
