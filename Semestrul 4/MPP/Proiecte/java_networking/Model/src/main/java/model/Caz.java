package model;

public class Caz extends Entity<Integer> {
    private String nume_caz;
    private String descriere;

    public Caz(int id, String nume_caz, String descriere) {
        this.id = id;
        this.nume_caz = nume_caz;
        this.descriere = descriere;
    }

    public Caz(String nume_caz, String descriere) {
        this.nume_caz = nume_caz;
        this.descriere = descriere;
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
