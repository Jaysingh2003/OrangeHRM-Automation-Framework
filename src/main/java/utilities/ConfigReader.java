package utilities;

import constants.FrameworkConstants;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;

    static {
        try (FileInputStream fis = new FileInputStream(FrameworkConstants.CONFIG_PATH)) {
            properties = new Properties();
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties: " + e.getMessage());
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

    public static String getBaseUrl()   { return get("base.url"); }
    public static String getBrowser()   { return get("browser"); }
    public static String getUsername()  { return get("username"); }
    public static String getPassword()  { return get("password"); }
    public static boolean isHeadless()  { return Boolean.parseBoolean(get("headless")); }
}
