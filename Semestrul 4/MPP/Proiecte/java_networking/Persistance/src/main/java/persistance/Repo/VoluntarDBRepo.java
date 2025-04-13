package persistance.Repo;

import model.*;

import persistance.VoluntarRepo0;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;  // Asigură-te că ai adăugat dependența pentru BCrypt

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class VoluntarDBRepo implements VoluntarRepo0 {

    private JdbcUtils jdbcUtils;

    private static final Logger logger = LogManager.getLogger();

    public VoluntarDBRepo(Properties prop) {
        logger.info("Initializing VoluntarDBRepo with properties: {}", prop);
        jdbcUtils = new JdbcUtils(prop);
    }

    @Override
    public Voluntar save(Voluntar entity) {
        logger.traceEntry();
        String sql = "INSERT INTO VOLUNTARI (username, parola, nume_voluntar) VALUES (?, ?, ?)";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            String hashedPassword = BCrypt.hashpw(entity.getPassword(), BCrypt.gensalt());

            ps.setString(1, entity.getUsername());
            ps.setString(2, hashedPassword);
            ps.setString(3, entity.getNume_voluntar());

            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        }
        logger.traceExit();
        return entity;
    }


    @Override
    public Voluntar findOne(Integer id) {
        logger.traceEntry("findOne task{}, elem");
        Connection connection = jdbcUtils.getConnection();
        Voluntar voluntar = null;
        try (PreparedStatement ps = connection.prepareStatement("select * from VOLUNTARI where id_voluntar = ?")) {
            try (ResultSet rs = ps.executeQuery()) {
                String user = rs.getString("username");
                String pass = rs.getString("parola");
                String nume = rs.getString("nume_voluntar");
                voluntar = new Voluntar(user, pass, nume);
                voluntar.setId(id);
            }
        }catch (SQLException e) {
            logger.error(e);
            System.err.println("DB Error " + e);
        }
        logger.traceExit(voluntar);
        return voluntar;
    }

    @Override
    public Iterable<Voluntar> findAll() {
        logger.traceEntry("findAll task{}, elem");
        Connection connection = jdbcUtils.getConnection();
        List<Voluntar> voluntari = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("select * from VOLUNTARI")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Integer id = rs.getInt("id_voluntar");
                    String nume = rs.getString("nume_voluntar");
                    String user = rs.getString("username");
                    String pass = rs.getString("parola");

                    Voluntar voluntar = new Voluntar(user, pass, nume);
                    voluntar.setId(id);

                    voluntari.add(voluntar);
                }
            }
        }catch (SQLException e) {
            logger.error(e);
            System.err.println("DB Error " + e);
        }
        logger.traceExit(voluntari);
        return voluntari;
    }

    @Override
    public Voluntar update(Voluntar entity) {
        return null;
    }

    @Override
    public Optional<Voluntar> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public Voluntar deleteById(Integer integer) {
        return null;
    }

    @Override
    public Optional<Voluntar> existsById(Integer integer) {
        return Optional.empty();
    }


    @Override
    public Voluntar Login(String username, String password) {
        Voluntar voluntar = null;
        Connection connection = jdbcUtils.getConnection();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM VOLUNTARI WHERE username = ?")) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    Integer id = rs.getInt("id_voluntar");
                    String user = rs.getString("username");
                    String storedHash = rs.getString("parola");
                    String nume = rs.getString("nume_voluntar");
                    if (BCrypt.checkpw(password, storedHash)) {
                        voluntar = new Voluntar(user, storedHash, nume);
                        voluntar.setId(id);
                    } else {
                        logger.warn("Parola incorectă pentru username: " + username);
                    }
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("DB Error " + e);
        }
        logger.traceExit();
        return voluntar;
    }


    @Override
    public Voluntar findByUsername(String username) {
        logger.traceEntry("findByUsername task{}, elem");
        Connection connection = jdbcUtils.getConnection();
        Voluntar voluntar = null;
        try (PreparedStatement ps = connection.prepareStatement("select * from VOLUNTARI where username = ?")) {
            ps.setString(1, username);  // Setăm parametrul
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Am găsit rând!");
                    Integer id = rs.getInt("id_voluntar");
                    String pass = rs.getString("parola");
                    String nume = rs.getString("nume_voluntar");
                    voluntar = new Voluntar(id, username, pass, nume);
                }
                else{
                    System.out.println("NU am găsit rând pentru username ul" + username);
                }
            }

        } catch (SQLException e) {
            logger.error(e);
            System.err.println("DB Error " + e);
        }
        logger.traceExit(voluntar);
        return voluntar;
    }

}
