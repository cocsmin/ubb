package client.controllers;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Voluntar;
import services.*;

public class LoginController {

    private FadeTransition fadeOut;
    private IProjectServices server;
    private Stage stage;
    private Parent mainParent;
    private Voluntar voluntarL;

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;

    public void setServer(IProjectServices server) {
        this.server = server;
    }

    public void setParent(Parent parent) {
        this.mainParent = parent;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
//
//    private VoluntarService voluntarService;
//    private CazService cazService;
//    private DonatieService donatieService;
//    private DonatorService donatorService;

//    public void setVoluntarService(VoluntarService voluntarService) {
//        this.voluntarService = voluntarService;
//    }
//
//    public void setCazService(CazService cazService) {
//        this.cazService = cazService;
//    }
//
//    public void setDonatieService(DonatieService donatieService) {
//        this.donatieService = donatieService;
//    }
//
//    public void setDonatorService(DonatorService donatorService) {
//        this.donatorService = donatorService;
//    }

    @FXML
    public void handleLogin(javafx.event.ActionEvent actionEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        usernameField.clear();
        passwordField.clear();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/dashboard.fxml"));
            Parent root = fxmlLoader.load();

            DashboardController controller = fxmlLoader.getController();
            controller.setServer(server);
            controller.setStage(stage);
            this.voluntarL = server.login(username, password, controller);

            controller.initModel();
            controller.setVoluntarL(voluntarL);
            Stage newStage = new Stage();
            newStage.setTitle("Lista cazuri");
            newStage.setScene(new Scene(root));
            newStage.show();

            if (stage != null) {
                stage.close();
            }
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    }



//    private void openDashboard(Voluntar voluntar) {
//        try {
//
//            javafx.fxml.FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboard.fxml"));
//            javafx.scene.Parent root = loader.load();
//
//            DashboardController dashboardController = loader.getController();
//            dashboardController.setServices(cazService, donatieService, donatorService, voluntar);
//
//            Stage stage = new Stage();
//            stage.setTitle("Dashboard - Cazuri active");
//            stage.setScene(new javafx.scene.Scene(root));
//            stage.show();
//
//            Stage currentStage = (Stage) loginButton.getScene().getWindow();
//            currentStage.close();
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
