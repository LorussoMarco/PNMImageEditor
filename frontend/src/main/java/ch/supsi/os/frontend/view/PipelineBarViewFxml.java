package ch.supsi.os.frontend.view;

import ch.supsi.os.backend.application.ImageController;
import ch.supsi.os.backend.business.ImageModel;
import ch.supsi.os.frontend.controller.EventHandler;
import ch.supsi.os.frontend.controller.ImageEventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import ch.supsi.os.frontend.controller.TransformationPipelineController;

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
        TransformationPipelineController pipelineController = TransformationPipelineController.getInstance();
        ImageController imageController = ImageController.getInstance();
        ImageViewFxml imageViewFxml = ImageViewFxml.getInstance();

        bApply.setOnAction(e -> {
            ImageModel imageModel = imageController.getImageModel();
            if (imageModel != null && imageModel.getWidth() > 0 && imageModel.getHeight() > 0) {
                pipelineController.applyPipeline(imageModel);
                imageViewFxml.drawImage(imageModel);
                LogBarViewFxml.getInstance().addLogEntry("Transformation pipeline applied.");
            } else {
                LogBarViewFxml.getInstance().addLogEntry("No image loaded to apply pipeline.");
            }
        });

        bClear.setOnAction(e -> {
            pipelineController.clearPipeline();
            pipelineTextArea.clear();
            LogBarViewFxml.getInstance().addLogEntry("Pipeline cleared.");
        });
    }

    @Override
    public void update() {

    }

    public void updatePipelineTextArea() {
        TransformationPipelineController pipelineController = TransformationPipelineController.getInstance();
        pipelineTextArea.setText(pipelineController.getPipelineDescription());
    }
}