package persistance;

import model.Donatie;

public interface DonatieRepo0 extends Repository<Donatie, Integer> {
    int getSumaDonatiiPentruCaz(Integer id);
}
