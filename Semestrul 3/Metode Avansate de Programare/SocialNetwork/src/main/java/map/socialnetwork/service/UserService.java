package map.socialnetwork.service;

import map.socialnetwork.Observable;
import map.socialnetwork.Observer;
import map.socialnetwork.domain.*;
import map.socialnetwork.repository.Page;
import map.socialnetwork.repository.Pageable;
import map.socialnetwork.repository.PagingRepo;
import map.socialnetwork.validator.ValidationException;
import map.socialnetwork.events.*;
import map.socialnetwork.repository.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserService implements Observable<UserEvent> {



    private final PagingRepo<Long,User> repoUtilizator;
    private final Repository<Long, Friendship> repositoryPrietenie;
    private final List<Observer<UserEvent>> observers;

    public UserService(PagingRepo<Long, User> repo1, Repository<Long, Friendship> repo2) {
        this.repoUtilizator = repo1;
        this.repositoryPrietenie = repo2;
        this.observers = new ArrayList<>();
    }



    public Iterable<User> getAll()  throws SQLException {
        return repoUtilizator.findAll();
    }

    public Iterable<Friendship> getPrietenie()  throws SQLException {
        return repositoryPrietenie.findAll();
    }

    public Long getNewUserId()  throws SQLException {
        Long id = 0L;
        Long max = 0L;
        for(User u : getAll()){
            id = u.getId();
            if(id > max)
                max = id;
        }

        return max+1;
    }
    public int getNr() throws SQLException {
        Iterable<User> utilizatori = repoUtilizator.findAll();

        int k=0;

        for(User it: utilizatori){
            k++;
        }

        return k;

    }
    public User getOne(Long id) throws SQLException {
        for(User it: repoUtilizator.findAll()){
            if(it.getId().equals(id))
                return it;
        }
        return null;
    }

    public User addUtilizator(User user)  throws SQLException {
        user.setId(getNewUserId());
        repoUtilizator.save(user);
        return user;
    }

    public Optional<User> stergeUtilizator(Long id) {
        try {
            Optional<User> u = repoUtilizator.findOne(id);
            if (u.isEmpty())
                throw new IllegalArgumentException("Userul nu exista!");

            List<Long> toRemove = StreamSupport.stream(repositoryPrietenie.findAll().spliterator(), false)
                    .filter(f -> f.getIdUser1().equals(id) || f.getIdUser2().equals(id))
                    .map(Friendship::getId)
                    .collect(Collectors.toList());

            toRemove.forEach(repositoryPrietenie::delete);

            Optional<User> user = repoUtilizator.delete(id);

            u.ifPresent(userToRemove ->
                    userToRemove.getFriends().forEach(friend -> friend.removeFriend(u))
            );

            return user;
        } catch (IllegalArgumentException e) {
            System.out.println("User invalid!");
        }
        return Optional.empty();

    }

    public User findByFullName(String Nume, String Prenume) throws SQLException {
        for (User utilizator : repoUtilizator.findAll()) {
            if (utilizator.getNume().equals(Nume) && utilizator.getPrenume().equals(Prenume)) {
                return utilizator;
            }
        }
        return null;
    }

    @Override
    public void addObserver(Observer<UserEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<UserEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(UserEvent t) {
        observers.forEach(x -> x.update(t));
    }

    public Page<User> getAllUsersPaged(Pageable pageable)  throws SQLException {
        return repoUtilizator.findAll(pageable);
    }

}

