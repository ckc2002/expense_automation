package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
	private static final Properties properties = new Properties();
	
	public static void loadProperties(String env) {
        String environment = (env == null || env.isBlank()) ? "qa" : env;
        String filePath = "src/main/resources/config/" + environment + ".properties";

        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            properties.clear();
            properties.load(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file: " + filePath, e);
        }
    }
	
	public static String getProperty(String key) {
        return System.getProperty(key, properties.getProperty(key));
    }
	
	public static String getProperty(String key, String defaultValue) {
        return System.getProperty(key, properties.getProperty(key, defaultValue));
    }
	
	public static int getIntProperty(String key, int defaultValue) {
        String value = getProperty(key, String.valueOf(defaultValue));
        try {
            return Integer.parseInt(value.trim());
        } catch (Exception e) {
            System.out.println("[ConfigManager] Invalid integer value for key: " + key + " | Using default: " + defaultValue);
            return defaultValue;
        }
    }	
	
	public static boolean getBooleanProperty(String key, boolean defaultValue) {
        return Boolean.parseBoolean(getProperty(key, String.valueOf(defaultValue)));
    }
}
