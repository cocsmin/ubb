package model;


//@javax.persistence.Entity;
//@Table(name = "voluntari_orm")
public class Voluntar extends Entity<Integer> {
    private String username;
    private String password;
    private String nume_voluntar;

    public Voluntar(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Voluntar(int id, String username, String password, String nume_voluntar) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nume_voluntar = nume_voluntar;
    }

    public Voluntar(String username, String password, String nume_voluntar) {
        this.username = username;
        this.password = password;
        this.nume_voluntar = nume_voluntar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNume_voluntar() {
        return nume_voluntar;
    }

    public void setNume_voluntar(String nume_voluntar) {
        this.nume_voluntar = nume_voluntar;
    }

}
