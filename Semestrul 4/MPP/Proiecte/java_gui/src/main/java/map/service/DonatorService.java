package map.service;

import map.domain.Donator;
import map.repository.database.DonatorDBRepo;
import map.repository.database.DonatorRepo0;

import java.util.List;

public class DonatorService {
    private DonatorRepo0 donatorDBRepo;

    public DonatorService(DonatorDBRepo donatorDBRepo) {
        this.donatorDBRepo = donatorDBRepo;
    }

    public List<Donator> searchByName(String partialName){
        return donatorDBRepo.findByNameContaining(partialName);
    }

    public Donator findById(Integer id) {
        return null;
    }

    public void save(Donator donator) {
        donatorDBRepo.save(donator);
    }

    public Donator findByFullName(String numeDonator) {
        return donatorDBRepo.findByFullName(numeDonator);
    }
}
