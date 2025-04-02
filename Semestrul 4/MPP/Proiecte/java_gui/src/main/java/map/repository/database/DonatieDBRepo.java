package map.repository.database;

import map.domain.Caz;
import map.domain.Donatie;
import map.domain.Donator;
import map.domain.Voluntar;
import map.repository.JdbcUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class DonatieDBRepo implements DonatieRepo0{
    private JdbcUtils jdbcUtils;

    private static final Logger logger = LogManager.getLogger();
    private DonatorDBRepo donatorDBRepo;
    private CazDBRepo cazDBRepo;

//    private Voluntar voluntar;
//    private Donatie donatie;
//    private Caz caz;

    public DonatieDBRepo(Properties prop, DonatorDBRepo DR, CazDBRepo CR) {
        logger.info("Initializing DonatieDBRepo with properties: {}", prop);
        jdbcUtils = new JdbcUtils(prop);
        donatorDBRepo = DR;
        cazDBRepo = CR;
    }

    @Override
    public Donatie save(Donatie entity) {
        logger.traceEntry();
        String sql = "insert into DONATII values (?, ?, ?, ?)";
        try (
                Connection connection = jdbcUtils.getConnection();
        ){
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, entity.getDonator().getId());
            System.out.println("sunt aici");
            ps.setInt(2, entity.getCaz().getId());
            System.out.println("sunt tot aici");
            ps.setTimestamp(3, Timestamp.valueOf(entity.getData_donatie()));
            ps.setInt(4, entity.getSuma_donata());

            ps.executeUpdate();
            entity = null;
        }catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        }
        logger.traceExit();
        return entity;    }

    @Override
    public Donatie findOne(Integer integer) {
        logger.traceEntry("findOne task{}, elem");
        Connection connection = jdbcUtils.getConnection();
        Donatie donatie = null;
        try (PreparedStatement ps = connection.prepareStatement("select * from DONATII where id_donatie = ?")) {
            ps.setInt(1, integer);
            try (ResultSet rs = ps.executeQuery()) {
                Integer idD = rs.getInt("id_donator");
                Integer idC = rs.getInt("id_caz");
                LocalDateTime dataDonatie = rs.getTimestamp("data_donatie").toLocalDateTime();
                Integer suma = rs.getInt("suma_donata");
                Donator donator = donatorDBRepo.findOne(idD);
                Caz caz = cazDBRepo.findOne(idC);

                donatie = new Donatie(donator, caz, dataDonatie, suma);
                donatie.setId(integer);
            }
        }catch (SQLException e) {
            logger.error(e);
            System.err.println("DB Error " + e);
        }
        logger.traceExit(donatie);
        return donatie;
    }

    @Override
    public Iterable<Donatie> findAll() {
        logger.traceEntry("findAll task{}, elem");
        Connection connection = jdbcUtils.getConnection();
        List<Donatie> donatii = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("select * from DONATII")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Integer id = rs.getInt("id_donatie");
                    Integer idD = rs.getInt("id_donator");
                    Integer idC = rs.getInt("id_caz");
                    LocalDateTime dataDonatie = rs.getTimestamp("data_donatie").toLocalDateTime();
                    Integer suma = rs.getInt("suma_donata");
                    Donator donator = donatorDBRepo.findOne(idD);
                    Caz caz = cazDBRepo.findOne(idC);

                    Donatie donatie = new Donatie(donator, caz, dataDonatie, suma);
                    donatie.setId(id);
                    donatii.add(donatie);
                }
            }
        }catch (SQLException e) {
            logger.error(e);
            System.err.println("DB Error " + e);
        }
        logger.traceExit(donatii);
        return donatii;    }

    @Override
    public Donatie update(Donatie entity) {
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
        Connection connection = jdbcUtils.getConnection();
        int suma_totala = 0;
        try (PreparedStatement ps = connection.prepareStatement("select * from DONATII where id_caz = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Integer suma = rs.getInt("suma_donata");
                    suma_totala += suma;
                }
            }
        }catch (SQLException e) {
            logger.error(e);
            System.err.println("DB Error " + e);
        }
        logger.traceExit(suma_totala);
        return suma_totala;

    }
}
