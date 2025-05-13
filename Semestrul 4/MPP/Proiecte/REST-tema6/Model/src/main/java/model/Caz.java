package model;

import com.google.gson.annotations.SerializedName;
import jakarta.persistence.*;

@jakarta.persistence.Entity
@Table(name = "CAZURI")
public class Caz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_caz")
    private int id;

    @Column(name = "nume_caz", nullable = false)
    @SerializedName("nume_caz")
    private String nume_caz;

    @Column(name = "descriere_caz", nullable = false, columnDefinition = "TEXT")
    @SerializedName("descriere")
    private String descriere;

    public Caz() {}

    public Caz(int id, String nume_caz, String descriere) {
        this.id = id;
        this.nume_caz = nume_caz;
        this.descriere = descriere;
    }

    public Caz(String nume_caz, String descriere) {
        this.nume_caz = nume_caz;
        this.descriere = descriere;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume_caz() {
        return nume_caz;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setNume_caz(String nume_caz) {
        this.nume_caz = nume_caz;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    @Override
    public String toString(){
        return nume_caz;
    }
}
