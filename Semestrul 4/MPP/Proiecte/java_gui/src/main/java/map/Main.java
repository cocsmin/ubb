package map;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import map.controller.LoginController;
import map.domain.Voluntar;
import map.repository.database.CazDBRepo;
import map.repository.database.DonatieDBRepo;
import map.repository.database.DonatorDBRepo;
import map.repository.database.VoluntarDBRepo;
import map.service.CazService;
import map.service.DonatieService;
import map.service.DonatorService;
import map.service.VoluntarService;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            System.out.println(getClass().getResource("/login.fxml"));
            Parent root = loader.load();

            LoginController loginController = loader.getController();
            Properties props = new Properties();

            try {
                props.load(new FileReader("bd.config.properties"));
            } catch (IOException e) {
                System.out.println("Error loading configuration file" + e);
            }

            VoluntarDBRepo vrepo = new VoluntarDBRepo(props);
            VoluntarService vservice = new VoluntarService(vrepo);
            DonatorDBRepo drepo = new DonatorDBRepo(props);
            DonatorService dservice = new DonatorService(drepo);
            CazDBRepo crepo = new CazDBRepo(props);
            CazService cservice = new CazService(crepo);
            DonatieDBRepo donatierepo = new DonatieDBRepo(props, drepo, crepo);
            DonatieService donatieservice = new DonatieService(donatierepo);
            loginController.setVoluntarService(vservice);
            loginController.setCazService(cservice);
            loginController.setDonatieService(donatieservice);
            loginController.setDonatorService(dservice);

            primaryStage.setTitle("Login");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }catch (Exception e) {
            e.printStackTrace();
        }
        /*
        VoluntarDBRepo repo = new VoluntarDBRepo(props);



        Voluntar voluntar = new Voluntar("userTest", "passTest", "NumeTest");
        System.out.println("Se salvează voluntarul...");
        repo.save(voluntar);

        System.out.println("Se caută voluntarul cu id 1...");
        Voluntar found = repo.findOne(1);
        System.out.println("Voluntar găsit: " + found);

        System.out.println("Se afișează toți voluntarii...");
        Iterable<Voluntar> voluntari = repo.findAll();
        for (Voluntar v : voluntari) {
            System.out.println(v);
        }

        System.out.println("Test logger finalizat.");

         */
    }
}
