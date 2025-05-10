package client.controllers;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.CazDTO;
import model.Donatie;
import model.Voluntar;
import services.IProjectObserver;
import services.IProjectServices;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable, IProjectObserver {

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

    private IProjectServices server;
    private Stage stage;

    private boolean initialized = false;

    public void setServer(IProjectServices server) {
        this.server = server;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private Voluntar voluntarL;

    public void setVoluntarL(Voluntar voluntarL) {
        this.voluntarL = voluntarL;
    }

    /**
     * Metoda inițializează modelul de date:
     * - Preia lista de cazuri sub forma de DTO-uri (care includ suma strânsă)
     * - Configurează coloanele tabelului
     * - Actualizează tabelul
     */
    public void initModel() {
        try {
            List<CazDTO> dtoList = server.createCazDTOList();

            // Log pentru verificare
            System.out.println("Număr cazuri primite: " + dtoList.size());
            if (dtoList != null && !dtoList.isEmpty()) {
                System.out.println("Exemplu caz: " + dtoList.get(0).getNumeCaz() + " - " + dtoList.get(0).getSumaDons());
            }

            // Actualizează UI-ul pe firul JavaFX
            Platform.runLater(() -> {
                cazTable.setItems(FXCollections.observableArrayList(dtoList));
                cazNumeColumn.setCellValueFactory(cellData -> cellData.getValue().numeCazProperty());
                donationSumColumn.setCellValueFactory(cellData -> cellData.getValue().sumaDonsProperty().asObject());
                cazTable.refresh();
            });
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Eroare", "Nu s-a putut încărca lista de cazuri: " + e.getMessage());
        }
    }

//    @FXML
//    private void handleLogout(ActionEvent event) {
//        ((Node) (event.getSource())).getScene().getWindow().hide();
//        Platform.exit();
//    }

    /**
     * Deschide fereastra pentru înregistrarea donațiilor.
     */
    @FXML
    private void handleOpenDonation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/donatie.fxml"));
            Parent root = loader.load();

            DonatieController donationController = loader.getController();
            donationController.setServer(server);
            donationController.setObserver(this);

            Stage newStage = new Stage();
            newStage.setTitle("Înregistrare Donație");
            newStage.setScene(new Scene(root));
            donationController.setStage(newStage);
            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Eroare", "Nu s-a putut deschide fereastra de donație: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Metoda de tip observer care se apelează când o donație nouă este înregistrată.
     * Actualizează tabela și afișează o notificare.
     */
    @Override
    public void adauga(Donatie donatie) throws Exception {
        Platform.runLater(() -> {
            try {
                // Reîncarcă lista actualizată de cazuri de la server
                List<CazDTO> updatedList = server.createCazDTOList();
                cazTable.setItems(FXCollections.observableArrayList(updatedList));
                cazTable.refresh();
                showAlert("Succes", "Donația a fost adăugată!");
            } catch (Exception e) {
                showAlert("Eroare", "Actualizarea listei a eșuat: " + e.getMessage());
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialized = true;
        if (server != null) {
            initModel();
        }
        cazTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        cazTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Aici puteți adăuga logica de afișare a detaliilor pentru cazul selectat, dacă este necesar.
            }
        });
    }

    @FXML
    public void handleLogout(javafx.event.ActionEvent actionEvent) {

        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.close();
        System.exit(0);
    }
}
