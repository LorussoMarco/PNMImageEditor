package ch.supsi.os.frontend.view;

import ch.supsi.os.frontend.controller.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;

public class LogBarViewFxml implements ControlledFxView{

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
                URL fxmlUrl = LogBarViewFxml.class.getResource("/logbar.fxml");
                if (fxmlUrl != null) {
                    FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
                    fxmlLoader.setController(myself);
                    fxmlLoader.load();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return myself;
    }

    public Node getNode() {
        return logBarVBox;
    }

    public void initialize() {
        clearLogButton.setOnAction(e -> clearLog());
    }

    public void addLogEntry(String message) {
        logTextArea.appendText(message + "\n");
    }

    public void clearLog() {
        logTextArea.clear();
    }

    @Override
    public void initialize(EventHandler eventHandler) {
        clearLogButton.setOnAction(e -> clearLog());
    }

    @Override
    public void update() {

    }
}
