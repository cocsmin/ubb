package persistance.Repo;

import model.Caz;
import model.Donator;
import model.Donatie;
import persistance.DonatieRepo0;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class DonatieDBRepo implements DonatieRepo0 {
    private JdbcUtils jdbcUtils;
    private static final Logger logger = LogManager.getLogger();

    public DonatieDBRepo(Properties prop) {
        logger.info("Initializing DonatieDBRepo with properties: {}", prop);
        jdbcUtils = new JdbcUtils(prop);
    }

    @Override
    public Donatie save(Donatie entity) {
        logger.traceEntry();
        String sql = "INSERT INTO DONATII (id_donator, id_caz, data_donatie, suma_donata) VALUES (?, ?, ?, ?)";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, entity.getDonator().getId());
            ps.setInt(2, entity.getCaz().getId());
            ps.setTimestamp(3, Timestamp.valueOf(entity.getData_donatie()));
            ps.setInt(4, entity.getSuma_donata());

            ps.executeUpdate();

            // Obține ID-ul generat și actualizează entitatea
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        }
        logger.traceExit();
        return entity; // Returnează entitatea actualizată
    }

    @Override
    public Donatie findOne(Integer id) {
        logger.traceEntry("findOne task{}, elem");
        Donatie donatie = null;
        String sql = "SELECT d.id_donatie, d.data_donatie, d.suma_donata, " +
                "don.id_donator, don.nume_donator, don.adresa, don.telefon, " +
                "c.id_caz, c.nume_caz, c.descriere_caz " +
                "FROM DONATII d " +
                "JOIN DONATORI don ON d.id_donator = don.id_donator " +
                "JOIN CAZURI c ON d.id_caz = c.id_caz " +
                "WHERE d.id_donatie = ?";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int idD = rs.getInt("id_donator");
                    String numeDonator = rs.getString("nume_donator");
                    String adresa = rs.getString("adresa");
                    String telefon = rs.getString("telefon");
                    Donator donator = new Donator(numeDonator, adresa, telefon);
                    donator.setId(idD);

                    int idC = rs.getInt("id_caz");
                    String numeCaz = rs.getString("nume_caz");
                    String descriereCaz = rs.getString("descriere_caz");
                    Caz caz = new Caz(numeCaz, descriereCaz);
                    caz.setId(idC);

                    LocalDateTime dataDonatie = rs.getTimestamp("data_donatie").toLocalDateTime();
                    int suma = rs.getInt("suma_donata");
                    donatie = new Donatie(donator, caz, dataDonatie, suma);
                    donatie.setId(id);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("DB Error " + e);
        }
        logger.traceExit(donatie);
        return donatie;
    }

    @Override
    public Iterable<Donatie> findAll() {
        logger.traceEntry("findAll task{}, elem");
        List<Donatie> donatii = new ArrayList<>();
        String sql = "SELECT d.id_donatie, d.data_donatie, d.suma_donata, " +
                "don.id_donator, don.nume_donator, don.adresa, don.telefon, " +
                "c.id_caz, c.nume_caz, c.descriere_caz " +
                "FROM DONATII d " +
                "JOIN DONATORI don ON d.id_donator = don.id_donator " +
                "JOIN CAZURI c ON d.id_caz = c.id_caz";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id_donatie");

                int idD = rs.getInt("id_donator");
                String numeDonator = rs.getString("nume_donator");
                String adresa = rs.getString("adresa");
                String telefon = rs.getString("telefon");
                Donator donator = new Donator(numeDonator, adresa, telefon);
                donator.setId(idD);

                int idC = rs.getInt("id_caz");
                String numeCaz = rs.getString("nume_caz");
                String descriereCaz = rs.getString("descriere_caz");
                Caz caz = new Caz(numeCaz, descriereCaz);
                caz.setId(idC);

                LocalDateTime dataDonatie = rs.getTimestamp("data_donatie").toLocalDateTime();
                int suma = rs.getInt("suma_donata");
                Donatie donatie = new Donatie(donator, caz, dataDonatie, suma);
                donatie.setId(id);
                donatii.add(donatie);
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("DB Error " + e);
        }
        logger.traceExit(donatii);
        return donatii;
    }

    @Override
    public Donatie update(Donatie entity) {
        // Not implemented
        return null;
    }

    @Override
    public Optional<Donatie> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public Donatie deleteById(Integer integer) {
        return null;
    }

    @Override
    public Optional<Donatie> existsById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public int getSumaDonatiiPentruCaz(Integer id) {
        logger.traceEntry("getSumaDonatiiPentruCaz task{}, elem");
        int suma_totala = 0;
        String sql = "SELECT SUM(d.suma_donata) as total FROM DONATII d WHERE d.id_caz = ?";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    suma_totala = rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("DB Error " + e);
        }
        logger.traceExit(suma_totala);
        return suma_totala;
    }
}
