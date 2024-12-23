package map.socialnetwork;

import map.socialnetwork.domain.*;
import map.socialnetwork.events.*;
import map.socialnetwork.service.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import map.socialnetwork.validator.ValidationException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AdaugaController implements Observer<UserEvent>{
    private String nume;
    private String prenume;
    private UserService srvU;
    private PrietenieService srvP;
    private CerereService srvC ;
    Stage dialogStage;
    User user;
    ObservableList<String> cereri = FXCollections.observableArrayList();

    @FXML
    TextField searchField3;

    @FXML
    TextField searchField4;

    @FXML
    private Label resultLabel;

    @FXML
    Button sendReqButton;

    @FXML
    private ListView<String> listView;

//    @FXML
//    private Button cereri_prietenie;

    @FXML
    public void initialize() {
        this.nume = LoggedIn.getNume();
        this.prenume = LoggedIn.getPrenume();
        //cereri_prietenie.setVisible(false);
        listView.setCellFactory(param -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if(empty) {
                    setText(null);
                    setGraphic(null);
                }
                else {
                    HBox hbox = new HBox(10);
                    setText(item);
                    Button accept= new Button("Accepta cererea");
                    Button refuz= new Button("Refuza cererea");
                    accept.setOnAction(event->
                    {
                        try {
                            handleAccept(item);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    refuz.setOnAction(event->{
                        try {
                            handleDecline(item);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    hbox.getChildren().addAll(accept,refuz);
                    setGraphic(hbox);
                }
            }
        });
        listView.setItems(cereri);
//        try {
//            updateNotifButtonVisibility();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    private boolean isAccepting = false;

    public void handleAccept(String cerere) throws SQLException {
        if (!isAccepting) {
            isAccepting = true;
            try {
                String[] parts = cerere.split(" ");
                this.nume = LoggedIn.getNume();
                this.prenume = LoggedIn.getPrenume();
                String nume = parts[1];
                String prenume = parts[3];
                Long id_utilizator = getUserId(this.nume, this.prenume);
                Long id_posibil_prieten = getUserId(nume, prenume);
                boolean prietenieExistenta = false;

                for (Friendship p : srvP.getPrietenii()) {
                    if ((p.getIdUser2().equals(id_utilizator) && p.getIdUser1().equals(id_posibil_prieten)) ||
                            (p.getIdUser2().equals(id_posibil_prieten) && p.getIdUser1().equals(id_utilizator))) {

                        prietenieExistenta = true;
                        break;
                    }
                }

                if (prietenieExistenta) {
                    MessageUser.showMessage(null, Alert.AlertType.INFORMATION,
                            "Info", "Sunteti deja prieteni!");
                    return;
                }
                for (Cerere c : srvC.getAll()) {
                    if ((c.getId1().equals(id_posibil_prieten) && c.getId2().equals(id_utilizator)) ||
                    (c.getId1().equals(id_utilizator) && c.getId2().equals(id_posibil_prieten))) {
                        System.out.println("Cererea găsită: " + c.getId() + " cu status: " + c.getStatus());
                        Cerere nou = new Cerere(id_posibil_prieten, id_utilizator, c.getDate(), "accepted");
                        nou.setId(c.getId());
                        srvC.updateCerere(nou);
                        Friendship p = new Friendship(id_posibil_prieten, id_utilizator, LocalDateTime.now());
                        srvP.adaugaPrietenie(p);
                        break;
                    }
                }
            } catch (ValidationException e) {
                e.printStackTrace();
            } finally {
                isAccepting = false;
            }
        }
    }


    private boolean isDeclining = false;

    private void handleDecline(String cerere) throws SQLException {
        if (!isDeclining) {
            isDeclining = true;
            try {
                String[] parts = cerere.split(" ");
                ObservableList<String> filteredList = FXCollections.observableArrayList();
                String nume = parts[1];
                String prenume = parts[3];
                this.nume = LoggedIn.getNume();
                this.prenume = LoggedIn.getPrenume();
                Long id_utilizator = getUserId(this.nume, this.prenume);
                Long id_posibil_prieten = getUserId(nume, prenume);

                boolean cerereGasita = false;

                for (Cerere c : srvC.getAll()) {
                    if (c.getId1().equals(id_posibil_prieten) && c.getId2().equals(id_utilizator) && c.getStatus().equals("sent")) {
                        cerereGasita = true;
                        Cerere nou = new Cerere(id_posibil_prieten, id_utilizator, c.getDate(), "declined");
                        nou.setId(c.getId());
                        srvC.updateCerere(nou);
                        break;
                    }
                }

                if (!cerereGasita) {
                    MessageUser.showMessage(null, Alert.AlertType.INFORMATION,
                            "Info", "Cererea nu există sau a fost deja procesată!");
                    return;
                }

                for (Cerere c1 : srvC.getAll()) {
                    if (c1.getId2().equals(id_utilizator) && c1.getStatus().equals("sent")) {
                        User u = srvU.getOne(c1.getId1());
                        filteredList.add("Nume: " + u.getNume() + " Prenume: " + u.getPrenume() + " Date: " + c1.getDate() + " Status: " + c1.getStatus());
                    }
                }

                cereri.setAll(filteredList);
                listView.setItems(cereri);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                isDeclining = false;
            }
        }
    }

    public void setService(UserService srv1, PrietenieService srv2, CerereService srv3, Stage dialogStage) {
        this.srvU = srv1;
        this.srvP = srv2;
        this.srvC = srv3;
        this.dialogStage = dialogStage;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }
    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public Long getUserId(String nume, String prenume) throws SQLException {
        Long id = null;
        Iterable<User> users = srvU.getAll();
        List<User> usersList = StreamSupport.stream(users.spliterator(), false).collect(Collectors.toList());
        for (User u : usersList) {
            if (u.getNume().equals(nume) && u.getPrenume().equals(prenume)) {
                id = u.getId();
            }
        }
        return id;
    }

    @FXML
    public void handleSearch(ActionEvent event) throws SQLException {
        try{
            Long idPosibilPrieten = null;
            Long idUtilizator = null;
            String query1 = searchField3.getText().trim();
            String query2 = searchField4.getText().trim();

            this.nume = LoggedIn.getNume();
            this.prenume = LoggedIn.getPrenume();


            if (query1.isEmpty() || query2.isEmpty()) {
                MessageUser.showMessage(null, Alert.AlertType.INFORMATION,
                        "Search", "Nu ati completat datele!");
                return;
            }

            idUtilizator = getUserId(this.nume, this.prenume);

            if (idUtilizator == null) {
                MessageUser.showMessage(null, Alert.AlertType.INFORMATION,
                        "Search", "Utilizatorul nu a fost găsit.");
                System.out.println("sal");
                return;
            }

            idPosibilPrieten = getUserId(query1, query2);
            System.out.println(query2);
            if (idPosibilPrieten == null) {
                MessageUser.showMessage(null, Alert.AlertType.INFORMATION,
                        "Search", "Utilizatorul nu a fost găsit.");
                return;
            }


            boolean singur = false;
            if (idPosibilPrieten.equals(idUtilizator)) {
                singur = true;
            }

            boolean prietenieExistenta = false;
            //filtrare pe baza de date cu where NU aici!!!!!
            for (Friendship p : srvP.getPrietenii()) {
                if ((p.getIdUser2().equals(idUtilizator) && p.getIdUser1().equals(idPosibilPrieten)) || (p.getIdUser2().equals(idPosibilPrieten) && p.getIdUser1().equals(idUtilizator))) {
                    prietenieExistenta = true;
                    break;
                }
            }

            if (prietenieExistenta) {
                MessageUser.showMessage(null, Alert.AlertType.INFORMATION,
                        "Search", "Sunteti deja prieteni!");
            } else if (singur) {
                MessageUser.showMessage(null, Alert.AlertType.INFORMATION,
                        "Search", "Nu iti poti trimite singur cerere!");
            } else {
                resultLabel.setText(query1 + " " + query2);
                sendReqButton.setVisible(true);
            }

        }catch (Exception e) {

            e.printStackTrace();
            MessageUser.showErrorMessage(null, "A occurred: " + e.getMessage());
        }
    }

    @FXML
    public void handleAddNewFriend(ActionEvent event) throws SQLException {
        Long idPosibilPrieten = null;
        Long idUtilizator = null;
        this.nume = LoggedIn.getNume();
        this.prenume = LoggedIn.getPrenume();
        String query1 = searchField3.getText().trim();
        String query2 = searchField4.getText().trim();
        idUtilizator = getUserId(nume, prenume);
        idPosibilPrieten = getUserId(query1, query2);
        for (Cerere c : srvC.getAll()) {
            if (c.getId1().equals(idUtilizator) && c.getId2().equals(idPosibilPrieten) && c.getStatus().equals("sent")) {
                MessageUser.showMessage(null, Alert.AlertType.INFORMATION,
                        "Info", "Deja ai trimis o cerere acestei persoane!");
                return;
            }
        }

        Cerere c = new Cerere(idUtilizator, idPosibilPrieten, LocalDateTime.now(),"sent");
        srvC.addCerere(c);


    }

    @FXML
    public void handleSeeFrReq(ActionEvent event) throws SQLException {
        this.nume = LoggedIn.getNume();
        this.prenume = LoggedIn.getPrenume();
        Long id = getUserId(nume, prenume);
        ObservableList<String> filteredList = FXCollections.observableArrayList();
        boolean existaCereri = false;
        for(Cerere c: srvC.getAll())
        {
            if(c.getId2().equals(id) && c.getStatus().equals("sent")){
                User u = srvU.getOne(c.getId1());
                filteredList.add("Nume: "+ u.getNume() + " Prenume: " + u.getPrenume() + " Data: " + c.getDate()+ " Status: "+ c.getStatus());
                existaCereri = true;
            }

        }
        cereri.setAll(filteredList);
        listView.setItems(cereri);
        updateNotifButtonVisibility();
    }

    private void updateNotifButtonVisibility() throws SQLException {
        this.nume = LoggedIn.getNume();
        this.prenume = LoggedIn.getPrenume();
        Long id = this.srvU.findByFullName(nume, prenume).getId();
        //Long id = getUserId(nume, prenume);

        boolean existaCereri = false;
        for (Cerere cerere : srvC.getAll()) {
            if (cerere.getId2().equals(id) && cerere.getStatus().equals("sent"))
                existaCereri = true;
        }
        //cereri_prietenie.setVisible(existaCereri);
    }



    @Override
    public void update(UserEvent userEvent) {

    }
}
