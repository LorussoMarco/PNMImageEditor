package ch.supsi.os.frontend.view;

import ch.supsi.os.frontend.controller.LocalizationController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AboutView {

    @FXML
    private VBox aboutBox;

    @FXML
    private Label titleLabel;

    @FXML
    private Label versionLabel;

    @FXML
    private Label buildDateLabel;

    @FXML
    private Label developerLabel;

    @FXML
    private Button closeButton;

    private static AboutView instance;
    private final Properties properties;

    private AboutView() {
        properties = new Properties();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/about.fxml"));
            loader.setController(this);
            loader.load();

            // Load properties from application.properties
            try (InputStream input = getClass().getResourceAsStream("/application.properties")) {
                if (input != null) {
                    properties.load(input);
                } else {
                    throw new IOException("application.properties not found");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load About view", e);
        }
    }

    public static AboutView getInstance() {
        if (instance == null) {
            instance = new AboutView();
        }
        return instance;
    }

    public void showAboutDialog() {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/about.fxml"));
                loader.setController(this);
                VBox aboutBox = loader.load();

                Stage aboutStage = new Stage();
                aboutStage.initModality(Modality.APPLICATION_MODAL);
                aboutStage.setTitle(LocalizationController.getInstance().getLocalizedText("about.title"));

                Scene scene = new Scene(aboutBox);
                aboutStage.setScene(scene);

                closeButton.setOnAction(e -> aboutStage.close());
                update(); // Update UI with localized content
                aboutStage.showAndWait();

            } catch (IOException e) {
                throw new RuntimeException("Failed to load About dialog", e);
            }
        });
    }


    public void update() {
        LocalizationController localizationController = LocalizationController.getInstance();
        titleLabel.setText(localizationController.getLocalizedText("about.title"));
        versionLabel.setText(localizationController.getLocalizedText("about.version") + ": " +
                getAppProperty("application.version", "N/A"));
        buildDateLabel.setText(localizationController.getLocalizedText("about.build.date") + ": " +
                getAppProperty("build.timestamp", "N/A"));
        developerLabel.setText(localizationController.getLocalizedText("about.developers") + ": " +
                getAppProperty("developers", "N/A"));
        closeButton.setText(localizationController.getLocalizedText("about.close"));
    }

    private String getAppProperty(String key, String defaultValue) {
        return LocalizationController.getInstance().getAppProperty(key, defaultValue);
    }
}
