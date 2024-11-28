package ch.supsi.os.frontend.model;

import java.util.Locale;
import java.util.Properties;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LocalizationModel {

    private Locale locale;
    private static final Path PREFS_FILE_PATH = Paths.get(System.getProperty("user.home"), "user_preferences.txt");

    public LocalizationModel() {
        this.locale = Locale.ENGLISH;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public static String loadPreference(String key, String defaultValue) {
        Properties properties = new Properties();
        if (Files.exists(PREFS_FILE_PATH)) {
            try (var reader = Files.newBufferedReader(PREFS_FILE_PATH)) {
                properties.load(reader);
                return properties.getProperty(key, defaultValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return defaultValue;
    }
}
