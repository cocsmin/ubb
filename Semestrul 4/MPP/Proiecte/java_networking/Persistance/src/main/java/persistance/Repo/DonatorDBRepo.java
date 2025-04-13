package persistance.Repo;

import model.*;
import persistance.DonatorRepo0;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class DonatorDBRepo implements DonatorRepo0 {

    private JdbcUtils jdbcUtils;

    private static final Logger logger = LogManager.getLogger();

    public DonatorDBRepo(Properties prop) {
        logger.info("Initializing DonatorDBRepo with properties: {}", prop);
        jdbcUtils = new JdbcUtils(prop);
    }
    @Override
    public Donator save(Donator entity) {
        logger.traceEntry();
        String sql = "INSERT INTO DONATORI (nume_donator, adresa, telefon) VALUES (?, ?, ?)";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, entity.getNume_donator());
            ps.setString(2, entity.getAdresa());
            ps.setString(3, entity.getTelefon());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entity.setId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating donator failed, no ID obtained.");
                    }
                }
            } else {
                throw new SQLException("Creating donator failed, no rows affected.");
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error saving donator: " + e.getMessage());
        }
        logger.traceExit();
        return entity;
    }

    @Override
    public Donator findOne(Integer integer) {
        logger.traceEntry("findOne task{}, elem");
        Connection connection = jdbcUtils.getConnection();
        Donator donator = null;
        try (PreparedStatement ps = connection.prepareStatement("select * from DONATORI where id_donator = ?")) {
            ps.setInt(1, integer);
            try (ResultSet rs = ps.executeQuery()) {
                String nume = rs.getString("nume_donator");
                String adresa = rs.getString("adresa");
                String nrtel = rs.getString("telefon");
                donator = new Donator(nume, adresa, nrtel);
                donator.setId(integer);
            }
        }catch (SQLException e) {
            logger.error(e);
            System.err.println("DB Error " + e);
        }
        logger.traceExit(donator);
        return donator;
    }

    @Override
    public Iterable<Donator> findAll() {
        logger.traceEntry("findAll task{}, elem");
        Connection connection = jdbcUtils.getConnection();
        List<Donator> donatori = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("select * from DONATORI")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Integer id = rs.getInt("id_donator");
                    String nume = rs.getString("nume_donator");
                    String adresa = rs.getString("adresa");
                    String nrtel = rs.getString("telefon");

                    Donator donator = new Donator(nume, adresa, nrtel);
                    donator.setId(id);

                    donatori.add(donator);
                }
            }
        }catch (SQLException e) {
                logger.error(e);
                System.err.println("DB Error " + e);
        }
        logger.traceExit(donatori);
        return donatori;
    }

    @Override
    public Donator update(Donator entity) {
        return null;
    }

    @Override
    public Optional<Donator> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public Donator deleteById(Integer integer) {
        return null;
    }

    @Override
    public Optional<Donator> existsById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public List<Donator> findByNameContaining(String partialName) {
        logger.traceEntry("findByNameContaining task{}, elem");
        List<Donator> donatori = new ArrayList<>();
        String sql = "SELECT * FROM DONATORI WHERE nume_donator LIKE ?";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + partialName + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Integer id = rs.getInt("id_donator");
                    String nume = rs.getString("nume_donator");
                    String adresa = rs.getString("adresa");
                    String telefon = rs.getString("telefon");
                    Donator donator = new Donator(nume, adresa, telefon);
                    donator.setId(id);
                    donatori.add(donator);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        }
        logger.traceExit(donatori);
        return donatori;
    }


    @Override
    public Donator findByFullName(String numeDonator) {
        logger.traceEntry("findByFullName task{}, elem");
        Connection connection = jdbcUtils.getConnection();
        Donator donator = null;
        try (PreparedStatement ps = connection.prepareStatement("select * from DONATORI where nume_donator = ?")) {
            ps.setString(1, numeDonator);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Integer integer = rs.getInt("id_donator");
                    String adresa = rs.getString("adresa");
                    String nrtel = rs.getString("telefon");
                    donator = new Donator(integer, numeDonator, adresa, nrtel);
                }
            }
        }catch (SQLException e) {
            logger.error(e);
            System.err.println("DB Error " + e);
        }
        logger.traceExit(donator);
        return donator;
    }
}
