package ch.supsi.os.frontend.view;

import ch.supsi.os.frontend.controller.LocalizationController;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

public class PreferencesView {

    private static final String PREFS_FILE_NAME = "user_preferences.txt";
    private static final Path PREFS_FILE_PATH = Paths.get(System.getProperty("user.home"), PREFS_FILE_NAME);

    public static void showPreferencesDialog() {
        Platform.runLater(() -> {
            Stage dialog = new Stage();
            dialog.setTitle(LocalizationController.getInstance().getLocalizedText("preferences.title"));
            dialog.initModality(Modality.APPLICATION_MODAL);


            ComboBox<String> languageComboBox = new ComboBox<>();
            List<String> languages = LocalizationController.getInstance().getAvailableLanguages();
            languageComboBox.getItems().addAll(languages);
            String currentLanguage = loadPreference("language", "en");
            languageComboBox.setValue(currentLanguage);

            GridPane gridPane = new GridPane();
            gridPane.setPadding(new Insets(10));
            gridPane.setVgap(10);
            gridPane.setHgap(10);

            gridPane.add(new Label(LocalizationController.getInstance().getLocalizedText("preferences.select.language")), 0, 0);
            gridPane.add(languageComboBox, 1, 0);
            gridPane.setId("preferencesGridPane");

            Button saveButton = new Button(LocalizationController.getInstance().getLocalizedText("preferences.save"));
            saveButton.setOnAction(e -> {
                String selectedLanguage = languageComboBox.getValue();
                PreferencesView.savePreferences(selectedLanguage);
                LocalizationController.getInstance().setLocale(Locale.forLanguageTag(selectedLanguage));
                dialog.close();
            });
            saveButton.setId("preferencesSaveButton");

            gridPane.add(saveButton, 0, 1, 2, 1);

            Scene dialogScene = new Scene(gridPane, 300, 150);
            dialog.setScene(dialogScene);
            dialog.showAndWait();
        });
    }

    public static void savePreferences(String language) {
        try (var writer = Files.newBufferedWriter(PREFS_FILE_PATH)) {
            Properties properties = new Properties();
            properties.setProperty("language", language);
            properties.store(writer, "User Preferences");
        } catch (Exception e) {
            e.printStackTrace();
        }
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
