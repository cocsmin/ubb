package map.repository.database;

import map.domain.Caz;
import map.domain.Donator;
import map.repository.JdbcUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class CazDBRepo implements CazRepo0{
    private JdbcUtils jdbcUtils;

    private static final Logger logger = LogManager.getLogger();

    public CazDBRepo(Properties prop) {
        logger.info("Initializing CazDBRepo with properties: {}", prop);
        jdbcUtils = new JdbcUtils(prop);
    }
    @Override
    public Caz save(Caz entity) {
        logger.traceEntry();
        String sql = "insert into CAZURI values (?, ?)";
        try (
                Connection connection = jdbcUtils.getConnection();
        ){
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, entity.getNume_caz());
            ps.setString(2, entity.getDescriere());

            ps.executeUpdate();
            entity = null;
        }catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        }
        logger.traceExit();
        return entity;    }

    @Override
    public Caz findOne(Integer integer) {
        logger.traceEntry("findOne task{}, elem");
        Connection connection = jdbcUtils.getConnection();
        Caz caz = null;
        try (PreparedStatement ps = connection.prepareStatement("select * from CAZURI where id_caz = ?")) {
            ps.setInt(1, integer);
            try (ResultSet rs = ps.executeQuery()) {
                String nume = rs.getString("nume_caz");
                String descriere = rs.getString("descriere_caz");
                caz = new Caz(nume, descriere);
                caz.setId(integer);
            }
        }catch (SQLException e) {
            logger.error(e);
            System.err.println("DB Error " + e);
        }
        logger.traceExit(caz);
        return caz;
    }

    @Override
    public Iterable<Caz> findAll() {
        logger.traceEntry("findAll task{}, elem");
        Connection connection = jdbcUtils.getConnection();
        List<Caz> cazuri = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("select * from CAZURI")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Integer id = rs.getInt("id_caz");
                    String nume = rs.getString("nume_caz");
                    String descriere = rs.getString("descriere_caz");

                    Caz caz = new Caz(id, nume, descriere);

                    cazuri.add(caz);
                }
            }
        }catch (SQLException e) {
            logger.error(e);
            System.err.println("DB Error " + e);
        }
        logger.traceExit(cazuri);
        return cazuri;
    }

    @Override
    public Caz update(Caz entity) {
        return null;
    }

    @Override
    public Optional<Caz> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public Caz deleteById(Integer integer) {
        return null;
    }

    @Override
    public Optional<Caz> existsById(Integer integer) {
        return Optional.empty();
    }
}
