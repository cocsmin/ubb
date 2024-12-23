package map.socialnetwork;


import javafx.scene.control.*;
import map.socialnetwork.domain.Friendship;
import map.socialnetwork.domain.User;
import map.socialnetwork.domain.LoggedIn;
import map.socialnetwork.events.EventType;
import map.socialnetwork.events.UserEvent;
import map.socialnetwork.repository.Pageable;
import map.socialnetwork.repository.Page;
import map.socialnetwork.repository.database.CerereDBRepo;

import map.socialnetwork.service.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import map.socialnetwork.repository.database.FriendshipDBRepo;
import map.socialnetwork.repository.database.UserDBRepo;
import map.socialnetwork.validator.ValidationException;


import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserController implements Observer<UserEvent>{

    UserDBRepo utilizatorDbRepo;
    FriendshipDBRepo prietenieDbRepo;
    CerereDBRepo cerereDbRepo;
    UserService srvU = new UserService(utilizatorDbRepo, prietenieDbRepo);
    PrietenieService srvP = new PrietenieService(utilizatorDbRepo, prietenieDbRepo);
    CerereService srvC = new CerereService(cerereDbRepo);

    ObservableList<User> model = FXCollections.observableArrayList();
    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    TableView<User> tableView;

    @FXML
    TableColumn<User, Long> tableUserId;

    @FXML
    TableColumn<User, String> tableUserFirstName;

    @FXML
    TableColumn<User, String> tableUserLastName;

    @FXML
    TextField searchField1;

    @FXML
    TextField searchField2;

    @FXML
    private Label notificareLabel;

    @FXML
    TextField numar_pagini;

    private int currentPage = 0;
    private int pageSize = 2;
    private int totalElements;

    @FXML
    private Button previousPageButton;

    @FXML
    private Button nextPageButton;

    private List<User> filtredUsers;

    @FXML
    public void initialize() {
        tableUserId.setCellValueFactory(new PropertyValueFactory<User, Long>("id"));
        tableUserFirstName.setCellValueFactory(new PropertyValueFactory<User, String>("Nume"));
        tableUserLastName.setCellValueFactory(new PropertyValueFactory<User, String>("Prenume"));
        tableView.setItems(model);
        numar_pagini.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (!newValue.isEmpty()) {
                    int newPageSize = Integer.parseInt(newValue.trim());
                    if (newPageSize > 0) {
                        pageSize = newPageSize;
                        currentPage = 0;
                        initModel();
                    } else {
                        notificareLabel.setText("Nr de pagini nu poate fi negativ");
                    }
                }
            } catch (NumberFormatException e) {
                notificareLabel.setText("Nr de pagini este invalid");
            } catch (SQLException e) {
                notificareLabel.setText("Eroare: " + e.getMessage());
            }
        });
        searchField1.textProperty().addListener((observable, oldValue, newValue) -> updateCautare());
        searchField2.textProperty().addListener((observable, oldValue, newValue) -> updateCautare());
    }

    @FXML
    public void handleNextPage() throws SQLException {
        if ((currentPage + 1) * pageSize < totalElements) {
            currentPage += 1;
            initModel();
        }
    }

    @FXML
    public void handlePreviousPage() throws SQLException {
        if (currentPage > 0){
            currentPage -= 1;
            initModel();
        }
    }

    @FXML
    public void handleActualizare() throws SQLException {
        String nrPagText = numar_pagini.getText().trim();
        int newPageSize = Integer.parseInt(nrPagText);
        if (newPageSize <= 0) {
            MessageUser.showErrorMessage(null, "Nr de pagini este invalid");
            return;
        }
        pageSize = newPageSize;
        currentPage = 0;
        initModel();
    }

    private void updatePaginationControls(){
        previousPageButton.setDisable(currentPage == 0);
        nextPageButton.setDisable((currentPage + 1) * pageSize >= totalElements);
    }

    @FXML
    public void handleAddUser(ActionEvent ev) {
        try {
            String query1 = LoggedIn.getNume();
            String query2 = LoggedIn.getPrenume();
            FXMLLoader fxmlLoader = new FXMLLoader(UserController.class.getResource("/adaugaCerere.fxml"));
            Parent root = fxmlLoader.load();

//            AdaugaController controller = fxmlLoader.getController();
//            controller.setService(srvU, srvP, srvC, primaryStage);

            Stage searchStage = new Stage();
            searchStage.setTitle("Add New Friend");
            searchStage.initModality(Modality.WINDOW_MODAL); // Fereastră modală
            searchStage.initOwner(primaryStage);
            searchStage.setScene(new Scene(root, 1000, 600));

            AdaugaController adaugaControler = fxmlLoader.getController();
            adaugaControler.setService(srvU,srvP,srvC,searchStage);
            adaugaControler.setNume(query1);
            adaugaControler.setPrenume(query2);

            searchStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleDeleteUser(ActionEvent ev) throws SQLException {
        User ut= srvU.findByFullName(LoggedIn.getNume(), LoggedIn.getPrenume());
        Long loggedInUserId = ut.getId();
        if (loggedInUserId == null) {
            MessageUser.showErrorMessage(null, "Utilizatorul nu exista");
            return;
        }

        User selectedUser = tableView.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            MessageUser.showErrorMessage(null, "Alegeti un utilizator pentru a sterge prietenia");
            return;
        }

        Long selectedUserId = selectedUser.getId();

        try {
            srvP.stergePrietenie(loggedInUserId, selectedUserId);

            MessageUser.showMessage(null, Alert.AlertType.INFORMATION,
                    "Sterge Prietenia",
                    "Prietenia dintre utilizatorul " + LoggedIn.getNume() + " "
                            + LoggedIn.getPrenume() + " si utilizatorul "
                            + selectedUser.getNume() + " " + selectedUser.getPrenume() + " a fost stearsa");

        } catch (ValidationException e) {
            MessageUser.showErrorMessage(null, "" + e.getMessage());
        } catch (Exception e) {
            MessageUser.showErrorMessage(null, "A aparut o eroare " + e.getMessage());
        }
    }

    @FXML
    public void handleUpdateButton(ActionEvent ev) {

        User userToUpdate = tableView.getSelectionModel().getSelectedItem();
        if (userToUpdate != null) {
            srvU.stergeUtilizator(userToUpdate.getId());
            //showAddUserEditDialog(null);
        } else MessageUser.showErrorMessage(null, "Select an User to update!");
    }

    @FXML
    public void handleSearch(ActionEvent event) throws SQLException {
        try {
            String query1 = searchField1.getText().trim();
            String query2 = searchField2.getText().trim();


            Iterable<User> users = srvU.getAll();
            List<User> usersList = StreamSupport.stream(users.spliterator(), false)
                    .collect(Collectors.toList());

            if (query1.isEmpty() && query2.isEmpty()) {
                model.setAll(usersList);
            } else {
                ObservableList<User> filteredList = FXCollections.observableArrayList();

                for (User user : usersList) {
                    if (user.getNume().contains(query1) || user.getPrenume().contains(query2)) {
                        Long id = user.getId();
                        for (Friendship p : srvP.getPrietenii()) {
                            Optional<User> u = Optional.empty();

                            if (p.getIdUser1().equals(id)) {
                                u = Optional.ofNullable(srvU.getOne(p.getIdUser2()));
                            } else if (p.getIdUser2().equals(id)) {
                                u = Optional.ofNullable(srvU.getOne(p.getIdUser1()));
                            }

                            u.ifPresent(filteredList::add);
                        }
                    }
                }

                model.setAll(filteredList);
            }

            tableView.setItems(model);

        } catch (Exception e) {
            e.printStackTrace();
            MessageUser.showErrorMessage(null, "A occurred: " + e.getMessage());
        }
    }

    @FXML
    private void handleOpenChat() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/chat.fxml"));
            Scene chatScene = new Scene(fxmlLoader.load());
            Stage chatStage = new Stage();
            chatStage.setScene(chatScene);
            chatStage.setTitle("Chat");
            chatStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setSocialNetwork(UserService su,PrietenieService sp,CerereService sc) throws SQLException {
        srvU = su;
        srvP = sp;
        srvC = sc;
        srvU.addObserver(this);
        srvP.addObserver(this);
        srvC.addObserver(this);
        initModel();
    }

    private void initModel() throws SQLException {
        Pageable pageable = new Pageable(currentPage, pageSize);
        Page<User> page = srvU.getAllUsersPaged(pageable);

        model.setAll((List<User>) page.getElementsOnPage());
        tableView.setItems(model);
        totalElements = page.getTotalElementCount();
        updatePaginationControls();
    }

    @Override
    public void update(UserEvent userEvent)  {
        try {
            initModel();
            if (userEvent.getType() == EventType.ADAUGA){
                notificareLabel.setText("Ai o noua cerere de prietenie");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateCautare() {
        try {
            String query1 = searchField1.getText().trim();
            String query2 = searchField2.getText().trim();

            Iterable<User> utilizatori = srvU.getAll();
            List<User> lista_utilizatori = StreamSupport.stream(utilizatori.spliterator(), false)
                    .collect(Collectors.toList());

            if (query1.isEmpty() && query2.isEmpty()) {
                model.setAll(lista_utilizatori);
            } else {
                ObservableList<User> lista = FXCollections.observableArrayList();
                for (User utilizator : lista_utilizatori) {
                    if (utilizator.getNume().contains(query1) || utilizator.getPrenume().contains(query2)) {
                        Long id = utilizator.getId();
                        for (Friendship p : srvP.getPrietenii()) {
                            Optional<User> u = Optional.empty();

                            if (p.getIdUser1().equals(id)) {
                                u = Optional.ofNullable(srvU.getOne(p.getIdUser2()));
                            } else if (p.getIdUser2().equals(id)) {
                                u = Optional.ofNullable(srvU.getOne(p.getIdUser1()));
                            }
                            u.ifPresent(lista::add);
                        }
                    }
                }
                model.setAll(lista);
            }
            tableView.setItems(model);
        } catch (Exception e) {
            e.printStackTrace();
            notificareLabel.setText("Eroare: " + e.getMessage());
        }
    }
}
