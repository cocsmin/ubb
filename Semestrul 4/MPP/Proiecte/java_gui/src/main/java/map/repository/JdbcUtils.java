package map.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {

    private Properties jdbcProperties;

    private static final Logger logger = LogManager.getLogger();

    public JdbcUtils(Properties properties) {
        this.jdbcProperties = properties;
    }

    private Connection instance = null;

    private Connection getNewConnection(){
        logger.traceEntry();

        String url = jdbcProperties.getProperty("jdbc.url");
        String user = jdbcProperties.getProperty("jdbc.user");
        String password = jdbcProperties.getProperty("jdbc.password");
        logger.info("trying to connect to database... {}", url);
        logger.info("user: {}", user);
        logger.info("password: {}", password);
        Connection connection = null;
        try {
            if (user != null && password != null)
                connection = DriverManager.getConnection(url, user, password);
            else
                connection = DriverManager.getConnection(url);
        }catch (SQLException e){
            logger.error(e);
            System.out.println("Connection error " + e);
        }
        return connection;
    }

    public Connection getConnection(){
        logger.traceEntry();
        try {
            if (instance == null || instance.isClosed())
                instance = getNewConnection();
        }catch (SQLException e){
            logger.error(e);
            System.out.println("DB error " + e);
        }
        logger.traceExit(instance);
        return instance;
    }
}
