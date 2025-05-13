package model;
import jakarta.persistence.*;

@jakarta.persistence.Entity
@Table(name = "DONATORI")
public class Donator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_donator")
    private int id;

    @Column(name = "nume_donator", nullable = false)
    private String nume_donator;

    @Column(name = "adresa")
    private String adresa;

    @Column(name = "telefon")
    private String telefon;

    public Donator() {}

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
