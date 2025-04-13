package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.io.Serializable;


public class CazDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String numeCaz;
    private final int sumaDons;

    public CazDTO(String numeCaz, int sumaDons) {
        this.numeCaz = numeCaz;
        this.sumaDons = sumaDons;
    }

    public StringProperty numeCazProperty() {
        return new SimpleStringProperty(numeCaz);
    }

    public IntegerProperty sumaDonsProperty() {

        return new SimpleIntegerProperty(sumaDons);
    }

    public String getNumeCaz() {
        return numeCaz;
    }

//    public void setNumeCaz(String numeCaz) {
//        this.numeCaz.set(numeCaz);
//    }

    public int getSumaDons() {
        return sumaDons;
    }

//    public void setSumaDons(int sumaDons) {
//        this.sumaDons.set(sumaDons);
//    }

    @Override
    public String toString() {
        return numeCaz + " cu suma totala de: " + sumaDons;
    }
}
