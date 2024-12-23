package map.socialnetwork.service;

import map.socialnetwork.Observer;
import map.socialnetwork.Observable;
import map.socialnetwork.domain.*;
import map.socialnetwork.validator.ValidationException;
import map.socialnetwork.events.UserEvent;
import map.socialnetwork.repository.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.StreamSupport;

public class PrietenieService implements Observable<UserEvent> {
    private final Repository<Long,User> repoUtilizator;
    private final Repository<Long, Friendship> repositoryPrietenie;
    private final List<Observer<UserEvent>> observers;


    public PrietenieService(Repository<Long, User> repo1,Repository<Long, Friendship> repo2) {
        this.repoUtilizator = repo1;
        this.repositoryPrietenie = repo2;
        this.observers = new ArrayList<>();
    }

    public Iterable<Friendship> getPrietenii() throws SQLException {
        return repositoryPrietenie.findAll();
    }

    public boolean verificaExistenta(Long id) {
        Friendship prietenie = repositoryPrietenie.findOne(id).orElseThrow(() -> new ValidationException("Friendship doesn't exist!"));
        return prietenie != null;
    }

    public Friendship adaugaPrietenie(Friendship prietenie) throws SQLException {

        Long id_user1 = prietenie.getIdUser1();
        User user1 = repoUtilizator.findOne(id_user1).orElseThrow(() -> new ValidationException("User doesn't exist!"));
        Long id_user2 = prietenie.getIdUser2();
        User user2 = repoUtilizator.findOne(id_user2).orElseThrow(() -> new ValidationException("User doesn't exist!"));
        if(getPrietenii() != null){
            getPrietenii().forEach(p->{
                if(p.getIdUser1().equals(id_user1) && p.getIdUser2().equals(id_user2)){
                    throw new ValidationException("The friendship already exist! ");
                }
            });
            if (prietenie.getIdUser1().equals(prietenie.getIdUser2())) {
                throw new ValidationException("IDs can't be the same!!! ");
            }

        }
        repositoryPrietenie.save(prietenie);
        user1.addFriend(Optional.ofNullable(user2));
        user2.addFriend(Optional.of(user1));
        return null;
    }

    public Friendship stergePrietenie(Long id1, Long id2) {
        User user1 = repoUtilizator.findOne(id1).orElseThrow(() -> new ValidationException("User doesn't exist!"));
        User user2 = repoUtilizator.findOne(id2).orElseThrow(() -> new ValidationException("User doesn't exist!"));

        Long friendshipId = StreamSupport.stream(repositoryPrietenie.findAll().spliterator(), false)
                .filter(f -> (f.getIdUser1().equals(id1) && f.getIdUser2().equals(id2))
                        || (f.getIdUser1().equals(id2) && f.getIdUser2().equals(id1)))
                .map(Friendship::getId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Prietenia asta nu exista!"));

        repositoryPrietenie.delete(friendshipId);

        user1.removeFriend(Optional.ofNullable(user2));
        user2.removeFriend(Optional.ofNullable(user1));
        return null;
    }

    public int nrComunitati() throws SQLException {
        List<Long> utilizatoriVizitati = new ArrayList<>();
        AtomicInteger rez = new AtomicInteger();

        repoUtilizator.findAll().forEach(utilizator -> {
            Long userId = utilizator.getId();
            if (!utilizatoriVizitati.contains(userId)) {
                List<User> comunitateC = new ArrayList<>();
                try {
                    DFS(userId, utilizatoriVizitati,comunitateC);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                rez.set(rez.get() + 1);
            }
        });
        return rez.get();
    }



    private void DFS(Long userId, List<Long> vizitati, List<User> cCurenta) throws SQLException {

        vizitati.add(userId);

        Optional<User> user = repoUtilizator.findOne(userId);
        if(user.isPresent()) {
            cCurenta.add(user.get());

            List<Friendship> rez=new ArrayList<>();
            repositoryPrietenie.findAll().forEach(rez::add);
            List<Friendship> prietenii = rez;
            prietenii.stream()
                    .filter(prietenie -> prietenie.getIdUser1().equals(userId) || prietenie.getIdUser2().equals(userId))
                    .forEach(prietenie ->
                    {
                        Long prietenId = (prietenie.getIdUser2().equals(userId) ? prietenie.getIdUser2() : prietenie.getIdUser1());
                        if (!vizitati.contains(prietenId)) {
                            try {
                                DFS(prietenId, vizitati, cCurenta);
                            } catch (IllegalArgumentException | SQLException e) {
                                System.out.println("Invalid arguments! ");
                            }
                        }
                    });
        }
    }
    public String sociabila() throws SQLException {

        List<Long> vizitati = new ArrayList<>();

        AtomicInteger max= new AtomicInteger(-1);
        Iterable<User> itr = repoUtilizator.findAll();

        AtomicReference<List<User>> cSociala = new AtomicReference<>(new ArrayList<>());
        StringBuilder rez = new StringBuilder();

        itr.forEach(it->{
            if(!vizitati.contains(it.getId())){
                List<User> comunitateCurenta = new ArrayList<>();
                try {
                    DFS(it.getId(), vizitati, comunitateCurenta);
                } catch (IllegalArgumentException | SQLException e) {
                    System.out.println("Invalid arguments! ");
                }

                if (comunitateCurenta.size() > max.get()) {
                    max.set(comunitateCurenta.size());
                    cSociala.set(comunitateCurenta);
                }
            }
        });

        cSociala.get().forEach(user-> rez.append(user.getPrenume()).append(" ").append(user.getNume()).append("  "));

        return rez.toString();


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

}
