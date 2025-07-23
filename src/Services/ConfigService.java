package Services;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigService {
    private static final String CONFIG_FILE = "conf.properties";
    private static String storageType;

    static {
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            Properties props = new Properties();
            props.load(fis);
            storageType = props.getProperty("STORAGE_TYPE", "FILE").toUpperCase();
        } catch (IOException e) {
            storageType = "DATABASE"; // fallback default
        }
    }

    public static boolean useFileStorage() {
        return "FILE".equals(storageType);
    }

    public static boolean useDatabaseStorage() {
        return "DATABASE".equals(storageType);
    }
}