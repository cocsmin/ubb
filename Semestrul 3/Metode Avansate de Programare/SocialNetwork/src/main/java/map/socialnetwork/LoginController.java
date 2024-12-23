package map.socialnetwork;

import map.socialnetwork.domain.*;
import map.socialnetwork.service.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    private UserService utilizatorService;
    private PrietenieService prietenieService;
    private CerereService cerereService;
    private Stage primaryStage;

    @FXML
    private TextField cauta_nume;

    @FXML
    private TextField cauta_prenume;

    @FXML
    private PasswordField cauta_parola;

    @FXML
    private Label erori;

    private Runnable utilizator_autentificat;


    public void setService(UserService utilizatorService, Stage stage) {
        this.utilizatorService = utilizatorService;
        this.primaryStage = stage;
    }

    public void setutilizator_autentificat(Runnable utilizator_autentificat) {
        this.utilizator_autentificat = utilizator_autentificat;
    }

    @FXML
    private void handleLogin() {
        String Nume = cauta_nume.getText().trim();
        String Prenume = cauta_prenume.getText().trim();
        String password = cauta_parola.getText().trim();
        if (Nume.isEmpty() || Prenume.isEmpty() || password.isEmpty()) {
            erori.setText("Toate câmpurile trebuie completate");
            return;
        }
        try {
            User utilizator = utilizatorService.findByFullName(Nume, Prenume);
            if (utilizator != null && utilizator.getPassword().equals(password)) {
                LoggedIn.setNume(Nume);
                LoggedIn.setPrenume(Prenume);
                if (utilizator_autentificat != null) {
                    utilizator_autentificat.run();
                }
            } else {
                erori.setText("Nume, prenume sau parola gresite");
            }
        } catch (Exception e) {
            e.printStackTrace();
            erori.setText("Eroare la autentificare");
        }
    }
}
