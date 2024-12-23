package map.socialnetwork;

import map.socialnetwork.domain.User;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import map.socialnetwork.service.PrietenieService;
import map.socialnetwork.service.UserService;
import map.socialnetwork.validator.ValidationException;

import java.sql.SQLException;

public class AdaugaUtilizatorController {

    @FXML
    private TextField textFieldId;

    @FXML
    private TextField textFieldNume;

    @FXML
    private TextField textFieldPrenume;

    @FXML
    private TextField textFieldPassword;

    private UserService srvU;
    private PrietenieService srvP;
    Stage dialogStage;
    User user;

    @FXML
    private void initialize() {

    }

    public void setService(UserService srv1,PrietenieService srv2, Stage dialogStage, User user) {
        this.srvU = srv1;
        this.srvP = srv2;
        this.dialogStage = dialogStage;
        this.user = user;
        if (user != null) {
            setFields(user);
        }
    }

    @FXML
    public void handleSave() {
        String id = textFieldId.getText();
        String prenume = textFieldPrenume.getText();
        String nume = textFieldNume.getText();
        String password = textFieldPassword.getText();
        User user = new User(nume, prenume, password);
        user.setId(Long.parseLong(id));
        if (null == this.user)
            saveUser(user);
    }

    private void saveUser(User user) {
        try {
            User newUser = this.srvU.addUtilizator(user);
            if (newUser == null)
                dialogStage.close();
        } catch (ValidationException e) {

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        dialogStage.close();
    }


    private void setFields(User user) {
        textFieldId.setText(user.getId().toString());
        textFieldPrenume.setText(user.getPrenume());
        textFieldNume.setText(user.getNume());
    }

    @FXML
    public void handleCancel() {
        dialogStage.close();
    }


}

