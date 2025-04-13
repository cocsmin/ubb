package client;

import services.IProjectObserver;
import model.Donatie;
import java.util.ArrayList;
import java.util.List;

public class MultiObserver implements IProjectObserver {
    private final List<IProjectObserver> observers = new ArrayList<>();

    public void addObserver(IProjectObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IProjectObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void adauga(Donatie donatie) throws Exception {
        for (IProjectObserver observer : observers) {
            observer.adauga(donatie);
        }
    }

    @Override
    public void logoutNotification() {

    }
}
