package map.socialnetwork.repository.database;

import map.socialnetwork.domain.User;
import map.socialnetwork.repository.Page;
import map.socialnetwork.repository.Pageable;
import map.socialnetwork.repository.PagingRepo;
import map.socialnetwork.validator.UserValidator;

import java.sql.*;
import java.util.*;

public class UserDBRepo implements PagingRepo<Long, User> {

    UserValidator validator;
    String url_db;
    String user_db;
    String password_db;

    public UserDBRepo(UserValidator validator, String url_db, String user_db, String password_db) {
        this.validator = validator;
        this.url_db = url_db;
        this.user_db = user_db;
        this.password_db = password_db;
    }

    @Override
    public Optional<User> findOne(Long id) {
        String query = "SELECT * FROM users WHERE id = ?";
        User user = null;
        try (Connection connection = DriverManager.getConnection(url_db, user_db, password_db);
            PreparedStatement statement = connection.prepareStatement(query);){

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String nume = resultSet.getString("nume");
                String prenume = resultSet.getString("prenume");
                String password = resultSet.getString("password");
                user = new User(nume, prenume, password);
                user.setId(id);
            }

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(user);
    }

    public User findById(Long id) throws SQLException {
        String query = "SELECT * FROM users WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url_db, user_db, password_db);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String prenume = resultSet.getString("prenume");
                String nume = resultSet.getString("nume");
                String password = resultSet.getString("password");

                User utilizator = new User(nume, prenume, password);
                utilizator.setId(id);
                return utilizator;
            }
        }
        return null;
    }

    @Override
    public Iterable<User> findAll() {
        HashMap<Long, User> users = new HashMap<>();
        try (Connection connection = DriverManager.getConnection(url_db, user_db, password_db);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");
            ResultSet resultSet = statement.executeQuery()){

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String nume = resultSet.getString("nume");
                String prenume = resultSet.getString("prenume");
                String password = resultSet.getString("password");
                User user = new User(nume, prenume, password);
                user.setId(id);

                users.put(user.getId(), user);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users.values();
    }

    @Override
    public Optional<User> save(User entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Userul nu poate fi null!");
        }
        String query = "INSERT INTO users (nume, prenume, password) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url_db, user_db, password_db);
            PreparedStatement statement = connection.prepareStatement(query);) {
            statement.setString(1, entity.getNume());
            statement.setString(2, entity.getPrenume());
            statement.setString(3, entity.getPassword());
            statement.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.of(entity);
    }

    @Override
    public Optional<User> delete(Long id){
        String query = "DELETE FROM users WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url_db, user_db, password_db);
             PreparedStatement statement = connection.prepareStatement(query);) {
             statement.setLong(1, id);
             statement.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        User userDeSters = null;
        for (User user : findAll()) {
            if (Objects.equals(user.getId(), id)) {
                userDeSters = user;
            }
        }
        return Optional.ofNullable(userDeSters);
    }

    @Override
    public Optional<User> update(User entity) {
        return Optional.empty();

    }

    public User findByFullName(String Nume, String Prenume) throws SQLException {
        String query = "SELECT * FROM users WHERE nume = ? AND prenume = ?";
        Connection connection = DriverManager.getConnection(url_db, user_db, password_db);
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, Nume);
            stmt.setString(2, Prenume);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                Long id=resultSet.getLong(("id"));
                String prenume = resultSet.getString("nume");
                String nume = resultSet.getString("prenume");
                String password = resultSet.getString("password");

                User utilizator = new User(nume, prenume, password);
                utilizator.setId(id);
                return utilizator;
            } else {
                return null;
            }
        }
    }

    @Override
    public Page<User> findAll(Pageable pageable) throws SQLException {
        List<User> utilizatori = new ArrayList<>();
        String countQuery = "SELECT COUNT(*) AS total FROM users";
        String query = "SELECT * FROM users LIMIT ? OFFSET ?";
        int total = 0;

        try (Connection connection = DriverManager.getConnection(url_db, user_db, password_db);
             Statement countStatement = connection.createStatement();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet countResultSet = countStatement.executeQuery(countQuery);
            if (countResultSet.next()) {
                total = countResultSet.getInt("total");
            }
            statement.setInt(1, pageable.getPageSize());
            statement.setInt(2, pageable.getPageNumber() * pageable.getPageSize());

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String Nume = resultSet.getString("nume");
                String Prenume = resultSet.getString("prenume");
                String password = resultSet.getString("password");

                User u = new User(Nume, Prenume, password);
                u.setId(id);
                utilizatori.add(u);
            }
        }

        return new Page<>(utilizatori, total);
    }

}
