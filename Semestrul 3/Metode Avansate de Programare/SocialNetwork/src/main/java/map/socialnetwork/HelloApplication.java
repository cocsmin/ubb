package map.socialnetwork;

import map.socialnetwork.domain.*;
import map.socialnetwork.repository.PagingRepo;
import map.socialnetwork.validator.*;
import map.socialnetwork.repository.Repository;
import map.socialnetwork.repository.database.*;
import map.socialnetwork.service.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HelloApplication extends Application {
    String url = "jdbc:postgresql://localhost:5432/postgres";
    String username = "postgres";
    String password = "parolasmechera";
    UserDBRepo UDBRepo = new UserDBRepo(new UserValidator(), url, username, password);
    PagingRepo<Long, User> repoUtilizator = new UserDBRepo(new UserValidator(), url, username, password);
    Repository<Long, Friendship> repoPrietenie = new FriendshipDBRepo(new FriendshipValidator(UDBRepo), url, username, password);
    Repository<Long, Cerere> repoCerere = new CerereDBRepo(new CerereValidator(), url, username, password);
    UserService servU = new UserService(repoUtilizator, repoPrietenie);
    PrietenieService servP = new PrietenieService(repoUtilizator, repoPrietenie);
    CerereService servC = new CerereService(repoCerere);

    @Override
    public void start(Stage stage) throws IOException, SQLException {

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to connect to the PostgreSQL server.");
            e.printStackTrace();
        }
        try {
            initLoginView(stage);
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
        stage.setWidth(1000);
        stage.setTitle("Aplicatie");
        stage.show();
    }

    private void openUtilizatorWindow() throws IOException {
        FXMLLoader utilizatorLoader = new FXMLLoader(getClass().getResource("/utilizatori.fxml"));
        Scene utilizatorScene = new Scene(utilizatorLoader.load());

        Stage utilizatorStage = new Stage();
        utilizatorStage.setScene(utilizatorScene);
        utilizatorStage.setTitle("Utilizatori");
        utilizatorStage.show();

        UserController utilizatorController = utilizatorLoader.getController();
        try {
            utilizatorController.setSocialNetwork(servU, servP, servC);
            utilizatorController.setPrimaryStage(utilizatorStage);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void initLoginView(Stage primaryStage) throws IOException {
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Scene loginScene = new Scene(loginLoader.load());
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Login");
        primaryStage.show();

        LoginController loginController = loginLoader.getController();
        loginController.setService(servU, primaryStage);
        loginController.setutilizator_autentificat(() -> {
            try {
                openUtilizatorWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}