package model;

import com.google.gson.annotations.SerializedName;
import javafx.beans.property.*;
import java.io.Serializable;

public class CazDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("numeCaz") // Asigură maparea corectă a câmpului JSON "numeCaz"
    private String numeCaz;

    @SerializedName("sumaDons") // Asigură maparea corectă a câmpului JSON "sumaDons"
    private int sumaDons;

    // Constructor gol (obligatoriu pentru Gson)
    public CazDTO() {}

    public CazDTO(String numeCaz, int sumaDons) {
        this.numeCaz = numeCaz;
        this.sumaDons = sumaDons;
    }

    // Getteri și setteri
    public String getNumeCaz() {
        return numeCaz;
    }

    public void setNumeCaz(String numeCaz) {
        this.numeCaz = numeCaz;
    }

    public int getSumaDons() {
        return sumaDons;
    }

    public void setSumaDons(int sumaDons) {
        this.sumaDons = sumaDons;
    }

    // Proprietăți JavaFX (pentru legătura cu TableView)
    public StringProperty numeCazProperty() {
        return new SimpleStringProperty(numeCaz);
    }

    public IntegerProperty sumaDonsProperty() {
        return new SimpleIntegerProperty(sumaDons);
    }

    @Override
    public String toString() {
        return numeCaz + " cu suma totala de: " + sumaDons;
    }
}