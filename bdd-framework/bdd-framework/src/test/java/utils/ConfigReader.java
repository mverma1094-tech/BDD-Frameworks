package utils;

import java.io.InputStream;
import java.util.Properties;

/** Reads config.properties. A -D system property with the same key wins. */
public final class ConfigReader {

    private static final Properties PROPS = new Properties();

    static {
        try (InputStream in = ConfigReader.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (in == null) {
                throw new IllegalStateException("config.properties not found on classpath");
            }
            PROPS.load(in);
        } catch (Exception e) {
            throw new RuntimeException("Could not load config.properties", e);
        }
    }

    private ConfigReader() { }

    public static String get(String key) {
        String override = System.getProperty(key);
        // Surefire passes "${browser}" literally when -Dbrowser isn't given, so ignore unresolved values
        if (override != null && !override.isBlank() && !override.startsWith("${")) {
            return override;
        }
        return PROPS.getProperty(key);
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }
}
