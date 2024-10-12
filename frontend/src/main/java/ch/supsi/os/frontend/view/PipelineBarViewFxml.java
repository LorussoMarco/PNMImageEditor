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

public class PipelineBarViewFxml implements ControlledFxView {

    private static PipelineBarViewFxml myself;

    @FXML
    private VBox pipelineVBox;

    @FXML
    private TextArea pipelineTextArea;

    @FXML
    private Button bApply;

    @FXML
    private Button bClear;

    private PipelineBarViewFxml() {}

    public static PipelineBarViewFxml getInstance() {
        if (myself == null) {
            myself = new PipelineBarViewFxml();
            try{
                URL fxmlUrl = PipelineBarViewFxml.class.getResource("/pipelinebar.fxml");
                if(fxmlUrl != null){
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

    @Override
    public Node getNode() {
        return pipelineVBox;
    }

    @Override
    public void initialize(EventHandler eventHandler) {

    }

    @Override
    public void update() {

    }
}
