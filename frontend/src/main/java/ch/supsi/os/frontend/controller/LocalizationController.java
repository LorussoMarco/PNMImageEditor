package ch.supsi.os.frontend.controller;

import ch.supsi.os.frontend.model.LocalizationModel;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class LocalizationController {

    private static LocalizationController myself;
    private static final String PREFS_FILE_NAME = "user_preferences.txt";
    private static final Path PREFS_FILE_PATH = Paths.get(System.getProperty("user.home"), PREFS_FILE_NAME);
    private static final String LANGUAGES_RESOURCE_PATH = "/languages.properties";

    private LocalizationController() {
    }

    public static LocalizationController getInstance() {
        if (myself == null) {
            myself = new LocalizationController();
        }
        return myself;
    }

    public static String getLocalizedText(String key) {
        String language = getSelectedLanguage();
        Locale locale = new Locale(language);
        ResourceBundle bundle = ResourceBundle.getBundle("translations", locale);

        return bundle.getString(key);
    }



    public static String getSelectedLanguage() {
        Properties properties = new Properties();
        try {
            InputStream inputStream = LocalizationModel.class.getClassLoader().getResourceAsStream(PREFS_FILE_NAME);

            if (inputStream != null) {
                properties.load(inputStream);
            } else {

                URL resourceUrl = LocalizationModel.class.getClassLoader().getResource("");
                System.out.println(resourceUrl);
                if (resourceUrl != null) {
                    File resourceFolder = new File(resourceUrl.toURI());
                    File configFile = new File(resourceFolder, CONFIG_FILE_PATH);

                    try (OutputStream output = new FileOutputStream(configFile)) {
                        properties.setProperty("language", "en");
                        properties.setProperty("inputAreaSize", "80");
                        properties.setProperty("outputAreaSize", "25");
                        properties.store(output, null);
                    }
                }
            }
        } catch (IOException | URISyntaxException e) {
            return "en";
        }
        return properties.getProperty("language");
    }


}
