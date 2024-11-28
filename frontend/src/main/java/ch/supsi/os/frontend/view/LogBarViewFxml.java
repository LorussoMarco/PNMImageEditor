package ch.supsi.os.frontend.view;

import ch.supsi.os.frontend.controller.EventHandler;
import ch.supsi.os.frontend.controller.LocalizationController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class LogBarViewFxml implements ControlledFxView {

    private static LogBarViewFxml myself;

    @FXML
    private VBox logBarVBox;

    @FXML
    private TextArea logTextArea;

    @FXML
    private Button clearLogButton;

    private LogBarViewFxml() {}

    public static LogBarViewFxml getInstance() {
        if (myself == null) {
            myself = new LogBarViewFxml();
            try {
                FXMLLoader loader = new FXMLLoader(LogBarViewFxml.class.getResource("/logbar.fxml"));
                loader.setController(myself);
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return myself;
    }

    public Node getNode() {
        return logBarVBox;
    }

    public void addLogEntry(String message) {
        logTextArea.appendText(message + "\n");
    }

    public void clearLog() {
        logTextArea.clear();
    }

    @Override
    public void update() {
        LocalizationController localizationController = LocalizationController.getInstance();
        clearLogButton.setText(localizationController.getLocalizedText("logbar.clear"));
    }

    @Override
    public void initialize(EventHandler eventHandler) {
        update();
        clearLogButton.setOnAction(e -> clearLog());
    }
}
