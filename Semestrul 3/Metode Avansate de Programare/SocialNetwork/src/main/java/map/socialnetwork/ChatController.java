package map.socialnetwork;

import map.socialnetwork.domain.*;
import map.socialnetwork.validator.*;
import map.socialnetwork.repository.database.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class ChatController {

    private Message replyToMessage;

    @FXML
    private TextField cautare5;
    @FXML
    private TextField cautare6;
    @FXML
    private Button chooseButton;
    @FXML
    private Label utilizatoriAlesi;
    @FXML
    private ListView<String> utilizatoriList;

    private UserDBRepo repo_ut=new UserDBRepo(new UserValidator(), "jdbc:postgresql://localhost:5432/postgres","postgres","parolasmechera");

    private MessageRepoDB messageRepo = new MessageRepoDB("jdbc:postgresql://localhost:5432/postgres", "postgres", "parolasmechera");

    private List<User> utilizatori_conv=new ArrayList<>();

    @FXML
    private TextArea chat;
    @FXML
    private TextField mesaje;
    @FXML
    private Button sendButton;

    @FXML
    private Button replyButton;

    String prenume = LoggedIn.getPrenume();
    String nume = LoggedIn.getNume();

    public ChatController() {
    }

    @FXML
    private void initialize() {
//
    }


    public void load() {
        try {
            List<Message> allMessages = (List<Message>) messageRepo.findAll();
            //trimite filtrarea la baza de date!!!!!!!
            chat.clear();

            for (User utilizator : utilizatori_conv) {
                String convNume = utilizator.getPrenume();
                String convPrenume = utilizator.getNume();

                chat.appendText("Conversatie cu " + convNume + " " + convPrenume + ":\n");

                for (Message message : allMessages) {
                    boolean primeste = message.getTo().stream()
                            .anyMatch(user -> user.getNume().equalsIgnoreCase(nume) &&
                                    user.getPrenume().equalsIgnoreCase(prenume)) &&
                            message.getFrom().getNume().equalsIgnoreCase(convNume) &&
                            message.getFrom().getPrenume().equalsIgnoreCase(convPrenume);

                    boolean trimite = message.getFrom().getNume().equalsIgnoreCase(nume) &&
                            message.getFrom().getPrenume().equalsIgnoreCase(prenume) &&
                            message.getTo().stream()
                                    .anyMatch(user -> user.getNume().equalsIgnoreCase(convNume) &&
                                            user.getPrenume().equalsIgnoreCase(convPrenume));

                    if (primeste) {
                        chat.appendText("De la " + message.getFrom().getPrenume() + ": " +
                                message.getMessage());
                        if (message.getReply() != null) {
                            chat.appendText(" [Reply to: " + message.getReply().getMessage() + "]");
                        }
                        chat.appendText("\n");
                    }

                    if (trimite) {
                        chat.appendText("De la " + prenume + ": " +
                                message.getMessage());
                        if (message.getReply() != null) {
                            chat.appendText(" [Reply to: " + message.getReply().getMessage() + "]");
                        }
                        chat.appendText("\n");
                    }
                }

                chat.appendText("\n");
            }
        } catch (RuntimeException e) {
            chat.appendText("Eroare la încărcarea mesajelor: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }



    @FXML
    public void onChooseButtonClick() {
        String nume = cautare5.getText();
        String prenume = cautare6.getText();

        try {
            User utilizatorGasit = repo_ut.findByFullName(nume, prenume);
            utilizatori_conv.add(utilizatorGasit);
            if (utilizatorGasit != null) {
                utilizatoriList.getItems().add(utilizatorGasit.getNume() + " " + utilizatorGasit.getPrenume());
            } else {
                utilizatoriAlesi.setText("Nu a fost găsit niciun utilizator.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            utilizatoriAlesi.setText("Eroare la conectarea la baza de date.");
        }
        load();
    }

    @FXML
    public void onSendButtonClick() {
        String mesaj = mesaje.getText().trim();
        User ut;

        try {
            ut = repo_ut.findByFullName(nume, prenume);
            if (ut == null) {
                chat.appendText("Nu exista utilizatorul\n");
                return;
            }
            Message mes=new Message(ut, utilizatori_conv,mesaj,LocalDateTime.now());
            if (replyToMessage != null) {
                mes.setReply(replyToMessage);
                replyToMessage = null;
            }
            messageRepo.saveMessage(mes);
            for(User utilizator : utilizatori_conv)
            {
                chat.appendText("de la " +prenume  +" catre " + utilizator.getNume()+ ": " + mesaj + "\n");
            }

            mesaje.clear();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onReplyButtonClick() {
        String selectedMessageText = chat.getSelectedText();
        if (selectedMessageText.isEmpty()) {
            chat.appendText("Selecteaza un mesaj pentru reply. \n");
            return;
        }

        replyToMessage = findMessageByContent(selectedMessageText);
        if (replyToMessage == null) {
            chat.appendText("Mesajul selectat nu a fost gasit in sistem. \n");
        }else {
            chat.appendText("Se da reply la: " + replyToMessage.getMessage() + "\n");
        }
    }

    private Message findMessageByContent(String content) {
        try {
            List<Message> allMessages = (List<Message>) messageRepo.findAll();
            for (Message message : allMessages) {
                if (message.getMessage().equals(content)) {
                    return message;
                }
            }
        } catch (Exception e) {
            chat.appendText("Eroare la căutarea mesajului: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
        return null;
    }


    public void prepareReply(Message message) {
        replyToMessage = message;
        replyToMessage.setId(message.getId());
        mesaje.setPromptText("Reply la: " + message.getMessage());
    }

}
