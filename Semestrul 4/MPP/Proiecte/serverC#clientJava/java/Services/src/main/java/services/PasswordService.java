package services;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordService {
    public String hashPassword(String plainPassword) {
        // Generează un salt și criptează parola
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public boolean verifyPassword(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null) {
            return false;
        }
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
