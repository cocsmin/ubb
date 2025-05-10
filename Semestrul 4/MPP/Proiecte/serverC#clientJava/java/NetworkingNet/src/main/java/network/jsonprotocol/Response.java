package network.jsonprotocol;

import model.*;
import java.util.List;

public class Response {
    private ResponseType type;
    private String errorMessage;
    private Voluntar voluntar;
    private Caz caz;
    private Donator donator;
    private Donatie donatie;
    private int sumaPentruCaz;
    private List<Donator> donatori;
    private List<Caz> cazuri;
    private List<Voluntar> voluntari;
    private List<CazDTO> cazuriDTO;

    public ResponseType getType() {
        return type;
    }

    public void setType(ResponseType type) {
        this.type = type;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Voluntar getVoluntar() {
        return voluntar;
    }

    public void setVoluntar(Voluntar voluntar) {
        this.voluntar = voluntar;
    }

    public Caz getCaz() {
        return caz;
    }

    public void setCaz(Caz caz) {
        this.caz = caz;
    }

    public Donator getDonator() {
        return donator;
    }

    public void setDonator(Donator donator) {
        this.donator = donator;
    }

    public Donatie getDonatie() {
        return donatie;
    }

    public void setDonatie(Donatie donatie) {
        this.donatie = donatie;
    }

    public int getSumaPentruCaz() {
        return sumaPentruCaz;
    }

    public void setSumaPentruCaz(int sumaPentruCaz) {
        this.sumaPentruCaz = sumaPentruCaz;
    }

    public List<Donator> getDonatori() {
        return donatori;
    }

    public void setDonatori(List<Donator> donatori) {
        this.donatori = donatori;
    }

    public List<Caz> getCazuri() {
        return cazuri;
    }

    public void setCazuri(List<Caz> cazuri) {
        this.cazuri = cazuri;
    }

    public List<Voluntar> getVoluntari() {
        return voluntari;
    }

    public void setVoluntari(List<Voluntar> voluntari) {
        this.voluntari = voluntari;
    }

    public List<CazDTO> getCazuriDTO() {
        return cazuriDTO;
    }

    public void setCazuriDTO(List<CazDTO> cazuriDTO) {
        this.cazuriDTO = cazuriDTO;
    }

    @Override
    public String toString() {
        return String.format("[type=%s, error=%s, user=%s]",
                type, errorMessage, voluntar != null ? voluntar : "");
    }
}
