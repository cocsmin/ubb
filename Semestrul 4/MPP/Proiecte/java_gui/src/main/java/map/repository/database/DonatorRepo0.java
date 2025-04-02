package map.repository.database;

import map.domain.Donator;
import map.repository.Repository;

import java.util.List;

public interface DonatorRepo0 extends Repository<Donator, Integer> {
    List<Donator> findByNameContaining(String partialName);

    Donator findByFullName(String numeDonator);
}
