package map.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import map.domain.Caz;
import map.domain.CazDTO;
import map.domain.Voluntar;
import map.service.CazService;
import map.service.DonatieService;
import map.service.DonatorService;

import java.util.List;

public class DashboardController {

    @FXML
    private TableView<CazDTO> cazTable;
    @FXML
    private TableColumn<CazDTO, String> cazNumeColumn;
    @FXML
    private TableColumn<CazDTO, Integer> donationSumColumn;

    @FXML
    private Button logoutButton;
    @FXML
    private Button donationButton;

    private CazService cazService;
    private DonatieService donatieService;
    private DonatorService donatorService;
    private Voluntar loggedVoluntar;

    public void setServices(CazService cazService1, DonatieService donatieService1, DonatorService donatorService1, Voluntar v1) {
        this.cazService = cazService1;
        this.donatieService = donatieService1;
        this.donatorService = donatorService1;
        this.loggedVoluntar = v1;
        initModel();
    }

    private void initModel() {
        List<Caz> cazuri = cazService.findAll();
        List<CazDTO> dtoList = cazService.createCazDTOList(cazuri, donatieService);
        cazTable.setItems(FXCollections.observableArrayList(dtoList));

        cazNumeColumn.setCellValueFactory(cellData -> cellData.getValue().numeCazProperty());
        donationSumColumn.setCellValueFactory(cellData -> cellData.getValue().sumaDonsProperty().asObject());
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleOpenDonation(ActionEvent event) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/donatie.fxml"));
            javafx.scene.Parent root = loader.load();

            DonatieController donationController = loader.getController();
            donationController.setServices(donatieService, donatorService, cazService);

            Stage stage = new Stage();
            stage.setTitle("Inregistrare Donatie");
            stage.setScene(new javafx.scene.Scene(root));
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}



