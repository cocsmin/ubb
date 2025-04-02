package map.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Stage;
import map.domain.Voluntar;
import map.service.CazService;
import map.service.DonatieService;
import map.service.DonatorService;
import map.service.VoluntarService;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;

    private VoluntarService voluntarService;
    private CazService cazService;
    private DonatieService donatieService;
    private DonatorService donatorService;

    public void setVoluntarService(VoluntarService voluntarService) {
        this.voluntarService = voluntarService;
    }

    public void setCazService(CazService cazService) {
        this.cazService = cazService;
    }

    public void setDonatieService(DonatieService donatieService) {
        this.donatieService = donatieService;
    }

    public void setDonatorService(DonatorService donatorService) {
        this.donatorService = donatorService;
    }

    @FXML
    public void handleLogin(javafx.event.ActionEvent actionEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        try {
            Voluntar voluntar = voluntarService.login(username, password);
            openDashboard(voluntar);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    }

    private void openDashboard(Voluntar voluntar) {
        try {

            javafx.fxml.FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboard.fxml"));
            javafx.scene.Parent root = loader.load();

            DashboardController dashboardController = loader.getController();
            dashboardController.setServices(cazService, donatieService, donatorService, voluntar);

            Stage stage = new Stage();
            stage.setTitle("Dashboard - Cazuri active");
            stage.setScene(new javafx.scene.Scene(root));
            stage.show();

            Stage currentStage = (Stage) loginButton.getScene().getWindow();
            currentStage.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
