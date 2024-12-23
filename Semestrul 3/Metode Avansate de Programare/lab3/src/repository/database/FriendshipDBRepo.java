package repository.database;

import domain.Friendship;
import domain.User;
import validator.FriendshipValidator;
import repository.Repository;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class FriendshipDBRepo implements Repository<Long, Friendship> {

    FriendshipValidator frValidator;
    String url_db;
    String user_db;
    String password_db;

    public FriendshipDBRepo(FriendshipValidator frValidator, String url_db, String user_db, String password_db) {
        this.frValidator = frValidator;
        this.url_db = url_db;
        this.user_db = user_db;
        this.password_db = password_db;
    }

    @Override
    public Optional<Friendship> findOne(Long id) {
        String query = "SELECT * FROM friendships WHERE id = ?";
        Friendship friendship = null;
        try (Connection connection = DriverManager.getConnection(url_db, user_db, password_db);
             PreparedStatement statement = connection.prepareStatement(query);) {
             statement.setLong(1, id);
             ResultSet resultSet = statement.executeQuery();
             while (resultSet.next()){
                 Long idF1 = resultSet.getLong("idprieten1");
                 Long idF2 = resultSet.getLong("idprieten2");
                 Timestamp friendsfrom = resultSet.getTimestamp("friendsfrom");
                 friendship = new Friendship(idF1, idF2, new java.sql.Timestamp(friendsfrom.getTime()).toLocalDateTime());
                 friendship.setId(id);
             }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(friendship);
    }

    @Override
    public Iterable<Friendship> findAll() {
        Map<Long, Friendship> friendships = new HashMap<>();
        String query = "SELECT * FROM friendships";
        try (Connection connection = DriverManager.getConnection(url_db, user_db, password_db);
             PreparedStatement statement = connection.prepareStatement(query);) {
             ResultSet resultSet = statement.executeQuery();{
                 while (resultSet.next()){
                     Long id = resultSet.getLong("id");
                     Long idF1 = resultSet.getLong("idprieten1");
                     Long idF2 = resultSet.getLong("idprieten2");
                     Timestamp friendsfrom = resultSet.getTimestamp("friendsfrom");
                     Friendship friendship = new Friendship(idF1, idF2, new java.sql.Timestamp(friendsfrom.getTime()).toLocalDateTime());
                     friendship.setId(id);
                     friendships.put(friendship.getId(), friendship);
                 }
            }
            }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return friendships.values();
    }

    @Override
    public Optional<Friendship> save(Friendship entity) {
        if (entity == null) {
            throw new IllegalArgumentException("pritenia nu poate fi goala!");
        }
        String query = "INSERT INTO friendships (idprieten1, idprieten2, friendsfrom) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url_db, user_db, password_db);
             PreparedStatement statement = connection.prepareStatement(query);) {
             statement.setLong(1, entity.getIdUser1());
             statement.setLong(2, entity.getIdUser2());
             statement.setTimestamp(3, java.sql.Timestamp.valueOf(entity.getFriendsFrom()));
             statement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return Optional.of(entity);
    }

    @Override
    public Optional<Friendship> delete(Long id) {
        String query = "DELETE FROM friendships WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url_db, user_db, password_db);
             PreparedStatement statement = connection.prepareStatement(query);) {
             statement.setLong(1, id);
             statement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        Friendship DeSters = null;
        for (Friendship friendship : findAll()) {
            if (Objects.equals(friendship.getId(), id)) {
                DeSters = friendship;
            }
        }
        return Optional.ofNullable(DeSters);
    }

    @Override
    public Optional<Friendship> update(Friendship entity) {
        return Optional.empty();
    }


}
