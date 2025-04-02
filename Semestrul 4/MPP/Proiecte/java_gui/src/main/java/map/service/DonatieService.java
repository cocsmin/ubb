package map.service;

import map.domain.Donatie;
import map.repository.database.DonatieDBRepo;
import map.repository.database.DonatieRepo0;

public class DonatieService {

    private DonatieRepo0 donatieDBRepo;

    public DonatieService(DonatieDBRepo donatieDBRepo) {
        this.donatieDBRepo = donatieDBRepo;
    }

    public void saveDonatie(Donatie donatie) {
        donatieDBRepo.save(donatie);

    }

    public int getSumaDonatiiPentruCaz(Integer id) {
        return donatieDBRepo.getSumaDonatiiPentruCaz(id);
    }
}
