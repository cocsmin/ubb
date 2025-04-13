package model;

public class Donator extends Entity<Integer> {
    private String nume_donator;
    private String adresa;
    private String telefon;

    public Donator(int id, String nume_donator, String adresa, String telefon) {
        this.id = id;
        this.nume_donator = nume_donator;
        this.adresa = adresa;
        this.telefon = telefon;
    }

    public Donator(String nume_donator, String adresa, String telefon) {
        this.nume_donator = nume_donator;
        this.adresa = adresa;
        this.telefon = telefon;
    }

    public String getNume_donator() {
        return nume_donator;
    }

    public String getAdresa() {
        return adresa;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setNume_donator(String nume_donator) {
        this.nume_donator = nume_donator;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    @Override
    public String toString() {
        return nume_donator;
    }
}
