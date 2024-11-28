package ch.supsi.os.frontend.view;

import ch.supsi.os.backend.application.ImageController;
import ch.supsi.os.backend.business.ImageModel;
import ch.supsi.os.frontend.controller.EventHandler;
import ch.supsi.os.frontend.controller.ImageEventHandler;
import ch.supsi.os.frontend.controller.LocalizationController;
import ch.supsi.os.frontend.controller.TransformationPipelineController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.io.IOException;

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
            try {
                FXMLLoader loader = new FXMLLoader(PipelineBarViewFxml.class.getResource("/pipelinebar.fxml"));
                loader.setController(myself);
                loader.load();
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
    public void update() {
        LocalizationController localizationController = LocalizationController.getInstance();
        bApply.setText(localizationController.getLocalizedText("pipeline.apply"));
        bClear.setText(localizationController.getLocalizedText("pipeline.clear"));
    }

    @Override
    public void initialize(EventHandler eventHandler) {
        update();
        TransformationPipelineController pipelineController = TransformationPipelineController.getInstance();
        ImageController imageController = ImageController.getInstance();
        ImageViewFxml imageViewFxml = ImageViewFxml.getInstance();

        // Disabilita i bottoni all'avvio
        setButtonsDisabled(true);

        // Aggiungi listener per abilitare i bottoni al caricamento dell'immagine
        if (eventHandler instanceof ImageEventHandler) {
            ((ImageEventHandler) eventHandler).addOnImageLoadedListener(() -> setButtonsDisabled(false));
        }

        bApply.setOnAction(e -> {
            ImageModel imageModel = imageController.getImageModel();
            if (imageModel != null) {
                pipelineController.applyPipeline(imageModel);
                imageViewFxml.drawImage(imageModel);
                LogBarViewFxml.getInstance().addLogEntry(
                        LocalizationController.getInstance().getLocalizedText("pipeline.applied")
                );
            } else {
                LogBarViewFxml.getInstance().addLogEntry(
                        LocalizationController.getInstance().getLocalizedText("pipeline.noImage")
                );
            }
        });

        bClear.setOnAction(e -> {
            pipelineController.clearPipeline();
            pipelineTextArea.clear();
            LogBarViewFxml.getInstance().addLogEntry(
                    LocalizationController.getInstance().getLocalizedText("pipeline.cleared")
            );
        });
    }

    private void setButtonsDisabled(boolean disabled) {
        bApply.setDisable(disabled);
        bClear.setDisable(disabled);
    }


    public void updatePipelineTextArea() {
        TransformationPipelineController pipelineController = TransformationPipelineController.getInstance();
        pipelineTextArea.setText(pipelineController.getPipelineDescription());
    }
}
