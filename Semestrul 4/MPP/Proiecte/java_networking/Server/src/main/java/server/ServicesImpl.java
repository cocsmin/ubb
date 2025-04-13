package server;

import persistance.CazRepo0;
import persistance.DonatieRepo0;
import persistance.DonatorRepo0;
import persistance.VoluntarRepo0;
import services.*;
import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServicesImpl implements IProjectServices {

    private CazRepo0 cazRepo;
    private DonatorRepo0 donatorRepo;
    private DonatieRepo0 donatieRepo;
    private VoluntarRepo0 voluntarRepo;

    private Map<String, IProjectObserver> loggedObservers;

    public ServicesImpl(VoluntarRepo0 voluntarRepo1, DonatorRepo0 donatorRepo1, DonatieRepo0 donatieRepo1, CazRepo0 cazRepo) {
        this.voluntarRepo = voluntarRepo1;
        this.donatorRepo = donatorRepo1;
        this.donatieRepo = donatieRepo1;
        this.cazRepo = cazRepo;
        this.loggedObservers = new ConcurrentHashMap<>();
        loggedObservers = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized Voluntar login(String username, String password, IProjectObserver client) throws Exception {
        Voluntar voluntar = voluntarRepo.findByUsername(username);
        if (voluntar != null) {
            //&& voluntar.) {}
            PasswordService passwordService = new PasswordService();
            if (passwordService.verifyPassword(password, voluntar.getPassword())) {
                if (loggedObservers.containsKey(voluntar.getUsername()))
                    throw new Exception("This user is already logged in!");
                loggedObservers.put(username, client);
                return voluntar;
            }
            else {
                throw new Exception("Wrong password");
            }
        }
        throw new Exception("Failed login");
    }

    @Override
    public synchronized Donatie saveDonatie(Donatie donatie) throws Exception {
        Donatie saved = donatieRepo.save(donatie);
        notifyParticipantAdded(saved);
        return saved;
    }

    @Override
    public synchronized List<Caz> findAllCaz() throws Exception {
        return (List<Caz>) cazRepo.findAll();
    }

    @Override
    public synchronized List<CazDTO> createCazDTOList() throws Exception {
        List<Caz> cazuri = (List<Caz>) cazRepo.findAll();
        List<CazDTO> cazDTOList = new ArrayList<>();
        for (Caz caz : cazuri) {
            int sumaDonatii = donatieRepo.getSumaDonatiiPentruCaz(caz.getId());

            CazDTO dto = new CazDTO(caz.getNume_caz(), sumaDonatii);
            cazDTOList.add(dto);
        }
        return cazDTOList;
    }

    @Override
    public synchronized List<Donator> searchByName(String partialName){
        return donatorRepo.findByNameContaining(partialName);
    }

    @Override
    public synchronized Donator saveDonator(Donator donator) throws Exception {
        return donatorRepo.save(donator);
    }

    @Override
    public synchronized Donator findByFullName(String fullName) throws Exception {
        return donatorRepo.findByFullName(fullName);
    }

    @Override
    public synchronized int getSumaDonatiiPentruCaz(int cazId) throws Exception{
        return donatieRepo.getSumaDonatiiPentruCaz(cazId);
    }

    @Override
    public synchronized void logout(String username) throws Exception {
        IProjectObserver client = loggedObservers.remove(username);
        if (client == null) {
            throw new Exception("Utilizatorul nu este logat.");
        }
        //client.logoutNotification();
    }


    private ExecutorService notificationExecutor = Executors.newSingleThreadExecutor();

    private void notifyParticipantAdded(Donatie donatie) {
        notificationExecutor.submit(() -> {
            for (IProjectObserver observer : loggedObservers.values()) {
                try {
                    observer.adauga(donatie);
                    System.out.println("Notificare trimisă către: " + observer);
                } catch (Exception e) {
                    System.out.println("Eroare la notificare" + e.getMessage());
                }
            }
        });
    }


}
