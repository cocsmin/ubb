package map.socialnetwork.service;

import map.socialnetwork.Observable;
import map.socialnetwork.Observer;
import map.socialnetwork.domain.*;
import map.socialnetwork.repository.database.CerereDBRepo;
import map.socialnetwork.validator.ValidationException;
import map.socialnetwork.events.*;
import map.socialnetwork.repository.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CerereService implements Observable<UserEvent> {

    private final Repository<Long, Cerere> repoCerere;
    private final List<Observer<UserEvent>> observers;

    public CerereService(Repository<Long, Cerere> repoCerere) {
        this.repoCerere = repoCerere;
        observers = new ArrayList<>();
    }

    public Iterable<Cerere> getAll()  throws SQLException {
        return repoCerere.findAll();
    }

    public Long getNewCerereId()  throws SQLException {
        Long id = 0L;
        Long max = 0L;
        for(Cerere u : getAll()){
            id = u.getId();
            if(id > max)
                max = id;
        }

        return max+1;
    }
    public int getNr() throws SQLException {
        Iterable<Cerere> cereri = repoCerere.findAll();

        int k=0;

        for(Cerere it: cereri){
            k++;
        }

        return k;

    }
    public Cerere getOne(Long id) throws SQLException {
        for(Cerere it: repoCerere.findAll()){
            if(it.getId().equals(id))
                return it;
        }
        return null;
    }

    public Cerere addCerere(Cerere c)  throws SQLException {
        for (Cerere existing : getAll()) {
            if (existing.getId1().equals(c.getId1()) && existing.getId2().equals(c.getId2()) && existing.getStatus().equals("sent")) {
                throw new ValidationException("Cerere deja existentă între acești utilizatori!");
            }
        }
        c.setId(getNewCerereId());
        repoCerere.save(c);
        return c;
    }

    public Cerere stergeCerere(Long id){
        try {
            Cerere c = repoCerere.findOne(id).orElseThrow(() -> new ValidationException("Cerere doesn't exist!"));;
            List<Tuplu<Long, Long>> idsToDelete = new ArrayList<>();


            Cerere c1 = repoCerere.delete(id).orElseThrow(() -> new ValidationException("Cerere doesn't exist!"));
            return c1;
        }
        catch (IllegalArgumentException e) {
            System.out.println("Invalid user! ");
        }
        return null;
    }

    public void updateCerere(Cerere c)  throws SQLException {
        repoCerere.update(c);
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
