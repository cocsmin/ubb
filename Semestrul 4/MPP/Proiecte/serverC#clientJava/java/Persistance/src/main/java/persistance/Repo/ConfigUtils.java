package persistance.Repo;

import java.io.IOException;
import java.util.Properties;

public class ConfigUtils {
    public static Properties loadProperties(){
        Properties prop = new Properties();
        try {
            prop.load(ConfigUtils.class.getClassLoader().getResourceAsStream("bd.config"));
        }catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException("Failed to load database properties", e);
        }
        return prop;
    }
}
