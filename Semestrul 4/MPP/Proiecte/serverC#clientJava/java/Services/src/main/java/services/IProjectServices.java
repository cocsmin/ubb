package services;

import model.*;

import java.util.List;

public interface IProjectServices {
    Voluntar login(String username, String password, IProjectObserver client) throws Exception;

    //void logout(IProjectObserver client) throws Exception;
    Donatie saveDonatie(Donatie donatie) throws Exception;

    List<Caz> findAllCaz() throws Exception;

    List<CazDTO> createCazDTOList() throws Exception;

    List<Donator> searchByName(String partialName) throws Exception;

    Donator saveDonator(Donator donator) throws Exception;

    Donator findByFullName(String numeDonator) throws Exception;

    int getSumaDonatiiPentruCaz(int cazId) throws Exception;


    void logout(String username, IProjectObserver client) throws Exception;

    //List<Voluntar> findAllVoluntari() throws Exception;
}


