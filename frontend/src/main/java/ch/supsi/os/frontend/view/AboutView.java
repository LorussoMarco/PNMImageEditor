package ch.supsi.os.frontend.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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
    private Properties properties;


    private AboutView() {
        loadProperties();
        try {
            URL fxmlUrl = AboutView.class.getResource("/about.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            loader.setController(this);
            loader.load();
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

    private void loadProperties() {
        properties = new Properties();
        try (InputStream input = getClass().getResourceAsStream("/application.properties")) {
            if (input != null) {
                properties.load(input);
            } else {
                throw new RuntimeException("Properties file not found");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file", e);
        }
    }

    public void showAboutDialog() {
        Stage aboutStage = new Stage();
        aboutStage.initModality(Modality.APPLICATION_MODAL);
        aboutStage.setTitle("About");

        Scene scene = new Scene(aboutBox);
        aboutStage.setScene(scene);

        closeButton.setOnAction(e -> aboutStage.close());

        // Set dynamic content from properties
        titleLabel.setText("About " + getName());
        versionLabel.setText("Version: " + getVersion());
        buildDateLabel.setText("Build date: " + getBuildTimestamp());
        developerLabel.setText("Developed by: " + getDevelopers());

        aboutStage.showAndWait();
    }

    public String getName() {
        return properties.getProperty("application.name", "N/A");
    }

    public String getVersion() {
        return properties.getProperty("application.version", "N/A");
    }

    public String getBuildTimestamp() {
        return properties.getProperty("build.timestamp", "N/A");
    }

    public String getDevelopers() {
        return properties.getProperty("developers", "N/A");
    }
}
