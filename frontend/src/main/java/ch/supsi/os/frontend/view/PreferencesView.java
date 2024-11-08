package ch.supsi.os.frontend.view;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PreferencesView {

    private static final String PREFS_FILE_NAME = "user_preferences.txt";
    private static final Path PREFS_FILE_PATH = Paths.get(System.getProperty("user.home"), PREFS_FILE_NAME);
    private static final String LANGUAGES_RESOURCE_PATH = "/languages.properties";

    public static void showPreferencesDialog() {
        Stage dialog = new Stage();
        dialog.setTitle("Preferences");
        dialog.initModality(Modality.APPLICATION_MODAL);

        // Load languages dynamically from languages.properties
        ComboBox<String> languageComboBox = new ComboBox<>();
        List<String> languages = loadAvailableLanguages();
        languageComboBox.getItems().addAll(languages);



        // Layout for preferences dialog
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        gridPane.add(new Label("Select Language:"), 0, 0);
        gridPane.add(languageComboBox, 1, 0);

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            savePreferences(languageComboBox.getValue());
            dialog.close();
        });

        gridPane.add(saveButton, 0, 1, 2, 1);

        Scene dialogScene = new Scene(gridPane, 300, 150);
        dialog.setScene(dialogScene);
        dialog.showAndWait();
    }

    private static void savePreferences(String language) {
        try (FileWriter writer = new FileWriter(PREFS_FILE_PATH.toFile())) {
            Properties properties = new Properties();
            properties.setProperty("language", language);
            properties.store(writer, "User Preferences");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static List<String> loadAvailableLanguages() {
        List<String> languages = new ArrayList<>();
        Properties languageProperties = new Properties();
        try (InputStream inputStream = PreferencesView.class.getResourceAsStream(LANGUAGES_RESOURCE_PATH)) {
            if (inputStream != null) {
                languageProperties.load(inputStream);
                for (String key : languageProperties.stringPropertyNames()) {
                    languages.add(languageProperties.getProperty(key));
                }
            } else {
                System.err.println("Languages resource file not found. Defaulting to English.");
                languages.add("English");
            }
        } catch (IOException e) {
            System.err.println("Error loading languages: " + e.getMessage());
            languages.add("English");
        }
        return languages;
    }
}
