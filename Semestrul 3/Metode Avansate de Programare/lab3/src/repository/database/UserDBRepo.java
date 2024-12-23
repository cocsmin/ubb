package repository.database;

import domain.User;
import validator.UserValidator;
import validator.Validator;
import repository.Repository;

import java.sql.*;
import java.util.*;

public class UserDBRepo implements Repository<Long, User> {

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
                user = new User(nume, prenume);
                user.setId(id);
            }

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(user);
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
                User user = new User(nume, prenume);
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
        String query = "INSERT INTO users (nume, prenume) VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection(url_db, user_db, password_db);
            PreparedStatement statement = connection.prepareStatement(query);) {
            statement.setString(1, entity.getNume());
            statement.setString(2, entity.getPrenume());
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

}
