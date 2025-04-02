package map.domain;

import java.sql.Date;
import java.time.LocalDateTime;

public class Donatie extends Entity<Integer> {
    private Donator donator;
    private Caz caz;
    private LocalDateTime data_donatie;
    private int suma_donata;

    public Donatie(int id, Donator donator, Caz caz, LocalDateTime data_donatie, int suma_donata) {
        this.id = id;
        this.donator = donator;
        this.caz = caz;
        this.data_donatie = data_donatie;
        this.suma_donata = suma_donata;
    }



    public Donatie(Donator donator, Caz caz, LocalDateTime data_donatie, int suma_donata) {
        this.donator = donator;
        this.caz = caz;
        this.data_donatie = data_donatie;
        this.suma_donata = suma_donata;
    }



    public Donator getDonator() {
        return donator;
    }

    public Caz getCaz() {
        return caz;
    }

    public LocalDateTime getData_donatie() {
        return data_donatie;
    }

    public int getSuma_donata() {
        return suma_donata;
    }


    public void setDonator(Donator donator) {
        this.donator = donator;
    }

    public void setCaz(Caz caz) {
        this.caz = caz;
    }

    public void setData_donatie(LocalDateTime data_donatie) {
        this.data_donatie = data_donatie;
    }

    public void setSuma_donata(int suma_donata) {
        this.suma_donata = suma_donata;
    }
}
