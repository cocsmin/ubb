package validator;

import domain.User;
import java.util.Objects;

public class UserValidator implements Validator<User> {

    @Override
    public void validate(User user) throws ValidationException {
        String msj_eroare = "";
        if (user.getNume().isEmpty())
            msj_eroare += "Numele nu poate lipsi! ";
        if (user.getPrenume().isEmpty())
            msj_eroare += "Prenumele nu poate lipsi! ";

        System.out.println(msj_eroare);
        if (!msj_eroare.equals(""))
            throw new ValidationException(msj_eroare);
    }
}
