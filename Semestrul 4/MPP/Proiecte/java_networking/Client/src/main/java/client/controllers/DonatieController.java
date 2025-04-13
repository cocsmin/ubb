package client.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Caz;
import model.Donatie;
import model.Donator;
import services.IProjectObserver;
import services.IProjectServices;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

public class DonatieController implements Initializable{

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

    private IProjectServices server;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Se setează serverul și se populează ComboBox-ul cu lista de cazuri.
     */
    public void setServer(IProjectServices server) {
        this.server = server;
        try {
            List<Caz> cazuri = server.findAllCaz();
            cazComboBox.setItems(FXCollections.observableArrayList(cazuri));
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Eroare", "Nu s-au putut încărca cazurile: " + e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configurează celulele tabelei (dacă nu se face deja)
        donorNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNume_donator()));
        donorAddressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAdresa()));
        donorPhoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefon()));

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            String search = donorSearchField.getText();
            if (search != null && !search.trim().isEmpty()) {
                handleDonorSearch();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    /**
     * Caută donatori după nume parțial și actualizează tabela de donatori.
     */
    @FXML
    private void handleDonorSearch() {
        String partialName = donorSearchField.getText();

        // Creează un Task pentru a efectua căutarea în fundal
        Task<List<Donator>> task = new Task<List<Donator>>() {
            @Override
            protected List<Donator> call() throws Exception {
                return server.searchByName(partialName);
            }
        };

        task.setOnSucceeded(event -> {
            List<Donator> results = task.getValue();
            donorSearchTable.setItems(FXCollections.observableArrayList(results));
            donorNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNume_donator()));
            donorAddressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAdresa()));
            donorPhoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefon()));
        });

        task.setOnFailed(event -> {
            Throwable ex = task.getException();
            ex.printStackTrace();
            showAlert("Eroare", "Căutarea nu a putut fi realizată: " + ex.getMessage());
        });

        // Rulează task-ul într-un thread separat
        new Thread(task).start();
    }


    /**
     * La selectarea unui donator din tabel se completează câmpurile de detalii.
     */
    @FXML
    private void handleDonorSelect(MouseEvent event) {
        Donator selected = donorSearchTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            donorNameField.setText(selected.getNume_donator());
            donorAddressField.setText(selected.getAdresa());
            donorPhoneField.setText(selected.getTelefon());
        }
    }

    /**
     * Gestionează salvarea unei donații:
     * - Verifică dacă donatorul există; dacă nu, se salvează noul donator
     * - Creează și salvează donația, asociindu-i cazului selectat
     * - Se afișează un mesaj de confirmare
     */
    @FXML
    private void handleSaveDonation() {
        // Capturăm valorile din UI
        Caz cazSelectat = cazComboBox.getValue();
        int amount = Integer.parseInt(donationAmountField.getText());
        String numeDonator = donorNameField.getText();
        String adresaDonator = donorAddressField.getText();
        String telefonDonator = donorPhoneField.getText();

        // Task-ul pentru salvarea donatorului (dacă nu există deja)
        Task<Donator> donorTask = new Task<Donator>() {
            @Override
            protected Donator call() throws Exception {
                Donator existingDonor = server.findByFullName(numeDonator);
                if (existingDonor != null) {
                    return existingDonor;
                } else {
                    // Salvează noul donator
                    return server.saveDonator(new Donator(numeDonator, adresaDonator, telefonDonator));
                }
            }
        };

        donorTask.setOnSucceeded(ev -> {
            Donator donor = donorTask.getValue();
            // După ce donatorul este salvat sau găsit, lansează task-ul pentru salvarea donației
            Task<Void> donationTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    Donatie donation = new Donatie(donor, cazSelectat, LocalDateTime.now(), amount);
                    server.saveDonatie(donation);
                    return null;
                }
            };

            donationTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    showAlert("Succes", "Donație înregistrată cu succes!");
                    // Curățăm câmpurile
                    donationAmountField.clear();
                    donorNameField.clear();
                    donorAddressField.clear();
                    donorPhoneField.clear();
                });
            });

            donationTask.setOnFailed(e -> {
                Throwable ex = donationTask.getException();
                ex.printStackTrace();
                Platform.runLater(() -> {
                    showAlert("Eroare", "Eroare la salvarea donației: " + ex.getMessage());
                });
            });

            new Thread(donationTask).start();
        });

        donorTask.setOnFailed(ev -> {
            Throwable ex = donorTask.getException();
            ex.printStackTrace();
            Platform.runLater(() -> {
                showAlert("Eroare", "Eroare la salvarea donatorului: " + ex.getMessage());
            });
        });

        new Thread(donorTask).start();
    }





    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

//    @Override
//    public void adauga(Donatie donatie) throws Exception {
//        // Actualizează lista de donatori dacă este necesar.
//        Platform.runLater(() -> {
//            // Dacă câmpul de căutare nu e gol, re-execută căutarea
//            String search = donorSearchField.getText();
//            if (search != null && !search.trim().isEmpty()) {
//                handleDonorSearch();
//            }
//        });
//    }

    @FXML
    private void handleLogout(ActionEvent event) {
//        Task<Void> logoutTask = new Task<>() {
//            @Override
//            protected Void call() throws Exception {
//                try {
//                    server.logout(currentUser.getId()); // sau service.logout(currentUser)
//                    Platform.runLater(() -> {
//                        // Închizi fereastra curentă și deschizi login-ul
//                        Stage stage = (Stage) logoutButton.getScene().getWindow();
//                        stage.close();
//                        showLoginWindow();
//                    });
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    Platform.runLater(() -> {
//                        showAlert("Eroare", "Logout-ul a eșuat: " + e.getMessage());
//                    });
//                }
//                return null;
//            }
//        };
//        new Thread(logoutTask).start();
    }

}
