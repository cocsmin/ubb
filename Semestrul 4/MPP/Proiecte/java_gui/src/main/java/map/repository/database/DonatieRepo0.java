package map.repository.database;

import map.domain.Donatie;
import map.repository.Repository;

public interface DonatieRepo0 extends Repository<Donatie, Integer> {
    int getSumaDonatiiPentruCaz(Integer id);
}
