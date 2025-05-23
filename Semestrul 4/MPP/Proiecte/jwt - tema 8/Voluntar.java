package model;

import jakarta.persistence.*;
@jakarta.persistence.Entity(name = "Voluntar")
@Table(name = "VOLUNTARI")
public class Voluntar{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_voluntar")
    private Integer id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "parola", nullable = false)
    private String password;

    @Column(name = "nume_voluntar", nullable = false)
    private String nume_voluntar;

    public Voluntar() {}

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public String toString() {
        return nume_voluntar;
    }

}
