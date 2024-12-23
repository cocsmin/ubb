package validator;

import domain.Friendship;
import domain.User;
import repository.InMemoryRepo;
import repository.Repository;
import repository.database.UserDBRepo;

import java.util.Optional;

public class FriendshipValidator implements Validator<Friendship> {

    private final UserDBRepo repo;
    public FriendshipValidator(UserDBRepo repo) {
        this.repo = repo;
    }

    @Override
    public void validate(Friendship friendship) throws ValidationException {
        Optional<User> u1 = repo.findOne(friendship.getIdUser1());
        Optional<User> u2 = repo.findOne(friendship.getIdUser2());

        if (friendship.getIdUser1() == null || friendship.getIdUser2() == null)
            throw new ValidationException("ID ul nu poate fi gol! ");
        if (u1.isEmpty() || u2.isEmpty())
            throw new ValidationException("ID ul nu exista! ");
    }
}
