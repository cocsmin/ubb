package map.socialnetwork.repository.database;

import map.socialnetwork.domain.Cerere;
import map.socialnetwork.repository.Repository;
import map.socialnetwork.validator.CerereValidator;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class CerereDBRepo implements Repository<Long, Cerere> {
    private CerereValidator validator;
    private String url;
    private String username;
    private String password;

    public CerereDBRepo(CerereValidator validator, String url, String username, String password) {
        this.validator = validator;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Optional<Cerere> findOne(Long id) {
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("select * from friend_requests " +
                    "where id = ?");

        ) {
            statement.setInt(1, Math.toIntExact(id));
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                Long id1 = resultSet.getLong("sender_id");
                Long id2 = resultSet.getLong("receiver_id");
                LocalDateTime date = resultSet.getTimestamp("request_date").toLocalDateTime();
                String status = resultSet.getString("status");
                Cerere c = new Cerere(id1, id2, date, status);
                c.setId(id);
                return Optional.of(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Cerere> findAll() {
        Set<Cerere> cereri = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM friend_requests");
             ResultSet resultSet = statement.executeQuery()) {  // Folosește executeQuery pentru a obține ResultSet

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long id1 = resultSet.getLong("sender_id");
                Long id2 = resultSet.getLong("receiver_id");
                LocalDateTime date = resultSet.getTimestamp("request_date").toLocalDateTime();
                String status = resultSet.getString("status");
                Cerere c = new Cerere(id1, id2, date, status);
                c.setId(id);
                cereri.add(c);
            }
            return cereri;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Optional<Cerere> save(Cerere entity) {
        if (entity==null)
            throw new IllegalArgumentException("entity must be not null");

        validator.validate(entity);

        try{
            Connection connection = DriverManager.getConnection(url, username, password);
            String query = "INSERT INTO friend_requests(id, sender_id, receiver_id, request_date, status) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, entity.getId());
                statement.setLong(2, entity.getId1());
                statement.setLong(3, entity.getId2());
                statement.setTimestamp(4, Timestamp.valueOf(entity.getDate()));
                statement.setString(5, entity.getStatus());
                statement.executeUpdate();
            }}
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Cerere> delete(Long id) {
        if (findOne(id) == null)
            throw new IllegalArgumentException("ID inexistent");

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM friend_requests WHERE id =" + id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }
    @Override
    public Optional<Cerere> update(Cerere entity) {

        String query = "UPDATE friend_requests SET sender_id = ?, receiver_id = ?, request_date = ?, status = ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, entity.getId1());
            statement.setLong(2, entity.getId2());
            statement.setTimestamp(3, Timestamp.valueOf(entity.getDate()));
            statement.setString(4, entity.getStatus());
            statement.setLong(5, entity.getId());

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Cererea a fost actualizată cu succes.");
            } else {
                System.out.println("Nu s-au găsit cereri cu acest ID.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}
