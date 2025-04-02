package map.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import map.domain.Caz;
import map.domain.Donator;
import map.domain.Donatie;
import map.service.CazService;
import map.service.DonatieService;
import map.service.DonatorService;

import java.time.LocalDateTime;
import java.util.List;

public class DonatieController {

    @FXML
    private ComboBox<Caz> cazComboBox;
    @FXML
    private TextField donationAmountField;

    @FXML
    private TextField donorNameField;
    @FXML
    private TextField donorAddressField;
    @FXML
    private TextField donorPhoneField;

    @FXML
    private TextField donorSearchField;
    @FXML
    private TableView<Donator> donorSearchTable;
    @FXML
    private TableColumn<Donator, String> donorNameColumn;
    @FXML
    private TableColumn<Donator, String> donorAddressColumn;
    @FXML
    private TableColumn<Donator, String> donorPhoneColumn;

    @FXML
    private Button searchButton;
    @FXML
    private Button saveDonationButton;

    private DonatieService donatieService;
    private DonatorService donatorService;
    private CazService cazService;

    public void setServices(DonatieService donatieService, DonatorService donatorService, CazService cazService) {
        this.donatieService = donatieService;
        this.donatorService = donatorService;
        this.cazService = cazService;

        cazComboBox.setItems(FXCollections.observableArrayList(cazService.findAll()));
    }

    @FXML
    private void handleDonorSearch() {
        String partialName = donorSearchField.getText();
        List<Donator> results = donatorService.searchByName(partialName);
        donorSearchTable.setItems(FXCollections.observableArrayList(results));

        donorNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNume_donator()));
        donorAddressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAdresa()));
        donorPhoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefon()));

    }

    @FXML
    private void handleDonorSelect(MouseEvent event) {
        Donator selected = donorSearchTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            donorNameField.setText(selected.getNume_donator());
            donorAddressField.setText(selected.getAdresa());
            donorPhoneField.setText(selected.getTelefon());
        }
    }

    @FXML
    private void handleSaveDonation() {
        try {
            Caz cazSelectat = cazComboBox.getValue();
            int amount = Integer.parseInt(donationAmountField.getText());
            String numeDonator = donorNameField.getText();
            String adresaDonator = donorAddressField.getText();
            String telefonDonator = donorPhoneField.getText();
            Donatie donatie = null;
            Donator donator = new Donator(numeDonator, adresaDonator, telefonDonator);
            if (donatorService.findByFullName(numeDonator) != null)
                donatie = new Donatie(donatorService.findByFullName(numeDonator), cazSelectat, LocalDateTime.now(), amount);
            else {
                donatorService.save(donator);
                donatie = new Donatie(donator, cazSelectat, LocalDateTime.now(), amount);
            }
            donatieService.saveDonatie(donatie);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Donație înregistrată cu succes!");
            alert.showAndWait();
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Eroare la salvarea donației: " + ex.getMessage());
            alert.showAndWait();
        }
    }
}
