package map.domain;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CazDTO {
    private final StringProperty numeCaz;
    private final IntegerProperty sumaDons;

    public CazDTO(String numeCaz, int sumaDons) {
        this.numeCaz = new SimpleStringProperty(numeCaz);
        this.sumaDons = new SimpleIntegerProperty(sumaDons);
    }

    public StringProperty numeCazProperty() {
        return numeCaz;
    }

    public IntegerProperty sumaDonsProperty() {
        return sumaDons;
    }

    public String getNumeCaz() {
        return numeCaz.get();
    }

    public void setNumeCaz(String numeCaz) {
        this.numeCaz.set(numeCaz);
    }

    public int getSumaDons() {
        return sumaDons.get();
    }

    public void setSumaDons(int sumaDons) {
        this.sumaDons.set(sumaDons);
    }
}
