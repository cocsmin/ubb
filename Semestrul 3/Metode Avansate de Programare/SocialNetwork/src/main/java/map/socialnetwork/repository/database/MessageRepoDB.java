package map.socialnetwork.repository.database;

import map.socialnetwork.validator.UserValidator;

import map.socialnetwork.domain.*;
import map.socialnetwork.repository.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MessageRepoDB {
    private final String url;
    private final String username;
    private final String password;
    private UserDBRepo repository = new UserDBRepo(new UserValidator(), "jdbc:postgresql://localhost:5432/postgres", "postgres", "parolasmechera");

    public MessageRepoDB(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public void saveMessage(Message message) throws SQLException {
        String sql = "INSERT INTO messages (from_user_id, to_user_ids, message, data, reply_to_message_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, message.getFrom().getId());
            String toUserIds = String.join(",",
                    message.getTo().stream().map(u -> String.valueOf(u.getId())).toArray(String[]::new));
            statement.setString(2, toUserIds);

            statement.setString(3, message.getMessage());
            statement.setTimestamp(4, Timestamp.valueOf(message.getData()));

            if (message.getReply() != null) {
                statement.setLong(5, message.getReply().getId());
            } else {
                statement.setNull(5, Types.BIGINT);
            }

            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Nu s-a putut salva mesajul " + e.getMessage(), e);
        }
    }

    public Iterable<Message> findAll() {
        List<Message> messages = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM messages;");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long msgId = resultSet.getLong("id");
                Long fromUserId = resultSet.getLong("from_user_id");
                String toUserIds = resultSet.getString("to_user_ids");
                String messageText = resultSet.getString("message");
                LocalDateTime timestamp = resultSet.getTimestamp("data").toLocalDateTime();
                Long replyToMessageId = resultSet.getLong("reply_to_message_id");

                User from = repository.findById(fromUserId);
                List<User> to = Arrays.stream(toUserIds.split(","))
                        .map(Long::parseLong)
                        .map(id -> {
                            try {
                                return repository.findById(id);
                            } catch (SQLException e) {
                                throw new RuntimeException("Eroare la baza de date " + id, e);
                            }
                        })
                        .collect(Collectors.toList());
                Message reply = null;
                if (replyToMessageId != null && replyToMessageId > 0) {
                    reply = findById(replyToMessageId);
                }
                Message message = new Message(from, to, messageText, timestamp);
                message.setId(msgId);
                message.setReply(reply);
                messages.add(message);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la încărcarea mesajelor: " + e.getMessage(), e);
        }
        return messages;
    }

    public Iterable<Message> findMessagesBetween(Long user1Id, Long user2Id) {
        List<Message> messages = new ArrayList<>();
        String query = "SELECT * FROM messages " +
                "WHERE (from_user_id = ? AND to_user_ids LIKE ?) " +
                "OR (from_user_id = ? AND to_user_ids LIKE ?) " +
                "ORDER BY data ASC";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, user1Id);
            statement.setString(2, "%" + user1Id + "%");
            statement.setLong(3, user2Id);
            statement.setString(4, "%" + user2Id + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Long fromUserId = resultSet.getLong("from_user_id");
                    String toUserIds = resultSet.getString("to_user_ids");
                    String messageText = resultSet.getString("message");
                    LocalDateTime timestamp = resultSet.getTimestamp("data").toLocalDateTime();

                    User from = repository.findById(fromUserId);
                    List<User> to = Arrays.stream(toUserIds.split(","))
                            .map(Long::parseLong)
                            .map(id -> {
                                try {
                                    return repository.findById(id);
                                } catch (SQLException e) {
                                    throw new RuntimeException("Eroare la baza de date " + id, e);
                                }
                            })
                            .collect(Collectors.toList());

                    Message message = new Message(from, to, messageText, timestamp);
                    messages.add(message);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la încărcarea mesajelor: " + e.getMessage(), e);
        }
        return messages;



    }
    public Message findById(Long messageId) throws SQLException {
        String sql = "SELECT * FROM messages WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, messageId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Long fromUserId = resultSet.getLong("from_user_id");
                String toUserIds = resultSet.getString("to_user_ids");
                String messageText = resultSet.getString("message");
                LocalDateTime timestamp = resultSet.getTimestamp("data").toLocalDateTime();
                Long replyToMessageId = resultSet.getLong("reply_to_message_id");

                User from = repository.findById(fromUserId);

                List<User> to = Arrays.stream(toUserIds.split(","))
                        .map(Long::parseLong)
                        .map(id -> {
                            try {
                                return repository.findById(id);
                            } catch (SQLException e) {
                                throw new RuntimeException("Eroare la găsirea utilizatorului cu ID: " + id, e);
                            }
                        })
                        .collect(Collectors.toList());

                Message reply = null;
                if (replyToMessageId != null && replyToMessageId > 0) {
                    reply = findById(replyToMessageId);
                }

                Message message = new Message(from, to, messageText, timestamp);
                message.setReply(reply);
                message.setId(messageId);
                return message;
            }
        } catch (SQLException e) {
            throw new SQLException("Nu s-a putut găsi mesajul cu ID-ul " + messageId, e);
        }

        return null; // Dacă mesajul nu există
    }
    public void generateNextMessageId(Message m) throws SQLException {
        long nextId = -1;

        try (Connection connection = DriverManager.getConnection(url, username, password);) {
            String query = "SELECT MAX(id) FROM messages";
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                if (rs.next()) {
                    nextId = rs.getInt(1) + 1;
                } else {
                    nextId = 1;
                }
            }
        }
        m.setId(nextId);
    }

}
