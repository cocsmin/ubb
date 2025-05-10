package persistance;

import model.Donator;

import java.util.List;

public interface DonatorRepo0 extends Repository<Donator, Integer> {
    List<Donator> findByNameContaining(String partialName);

    Donator findByFullName(String numeDonator);
}
