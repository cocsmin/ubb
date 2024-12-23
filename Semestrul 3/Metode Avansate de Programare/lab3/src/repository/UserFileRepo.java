package repository;

import domain.User;
import validator.Validator;
import java.util.List;

public class UserFileRepo extends AbstractFileRepo<Long, User> {

    public UserFileRepo(String nume_fisier, Validator<User> validator) {
        super(nume_fisier, validator);
    }

    @Override
    public User extractEntity(List<String> atribute){
        User user = new User(atribute.get(1), atribute.get(2));
        user.setId(Long.parseLong(atribute.get(0)));

        return user;
    }

    @Override
    protected String createEntityAsString(User user) {
        return user.getId() + ';' + user.getNume() + ';' + user.getPrenume();
    }
}
