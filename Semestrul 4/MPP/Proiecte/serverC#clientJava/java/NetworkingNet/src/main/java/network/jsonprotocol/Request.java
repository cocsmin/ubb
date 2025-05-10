package network.jsonprotocol;

import model.*;

public class Request {
    private RequestType type;
    private Voluntar voluntar;
    private Donator donator;
    private Caz caz;
    private Donatie donatie;
    private String nume;
    private String password;
    private String pname;
    private String fname;
    private int idCaz;
    private int idDonator;
    private int sumaDonatas;

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public Voluntar getVoluntar() {
        return voluntar;
    }

    public void setVoluntar(Voluntar voluntar) {
        this.voluntar = voluntar;
    }

    public Donator getDonator() {
        return donator;
    }

    public void setDonator(Donator donator) {
        this.donator = donator;
    }

    public Caz getCaz() {
        return caz;
    }

    public void setCaz(Caz caz) {
        this.caz = caz;
    }

    public Donatie getDonatie() {
        return donatie;
    }

    public void setDonatie(Donatie donatie) {
        this.donatie = donatie;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public int getIdCaz() {
        return idCaz;
    }

    public void setIdCaz(int idCaz) {
        this.idCaz = idCaz;
    }

    public int getIdDonator() {
        return idDonator;
    }

    public void setIdDonator(int idDonator) {
        this.idDonator = idDonator;
    }

    public int getSumaDonatas() {
        return sumaDonatas;
    }

    public void setSumaDonatas(int sumaDonatas) {
        this.sumaDonatas = sumaDonatas;
    }

    @Override
    public String toString() {
        return String.format(
                "Request[type=%s, voluntar=%s, caz=%s, donator=%s, donatie=%s]",
                type, voluntar, caz, donator, donatie
        );
    }
}
