package ch.supsi.os.frontend.view;

import ch.supsi.os.backend.application.ImageController;
import ch.supsi.os.backend.business.*;
import ch.supsi.os.frontend.controller.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TransformationsViewFxml implements ControlledFxView {

    private static TransformationsViewFxml myself;

    @FXML
    private VBox transformationsBar;

    @FXML
    private Button bFlipUpDown;

    @FXML
    private Button bFlipSide;

    @FXML
    private Button bRotateC;

    @FXML
    private Button bRotateAC;

    @FXML
    private Button bNegative;

    private final Map<Class<? extends ImageTransformationStrategy>, String> transformationKeys;

    private TransformationsViewFxml() {
        transformationKeys = new HashMap<>();
        transformationKeys.put(FlipUpsideDownTransformation.class, "transformations.flipUpDown");
        transformationKeys.put(FlipSideToSideTransformation.class, "transformations.flipSide");
        transformationKeys.put(Rotate90ClockwiseTransformation.class, "transformations.rotateClockwise");
        transformationKeys.put(Rotate90AntiClockwiseTransformation.class, "transformations.rotateAntiClockwise");
        transformationKeys.put(NegativeTransformation.class, "transformations.negative");
    }

    public static TransformationsViewFxml getInstance() {
        if (myself == null) {
            myself = new TransformationsViewFxml();
            try {
                FXMLLoader loader = new FXMLLoader(TransformationsViewFxml.class.getResource("/transformationsbar.fxml"));
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
        return transformationsBar;
    }

    @Override
    public void update() {
        LocalizationController localizationController = LocalizationController.getInstance();
        bFlipUpDown.setText(localizationController.getLocalizedText("transformations.flipUpDown"));
        bFlipSide.setText(localizationController.getLocalizedText("transformations.flipSide"));
        bRotateC.setText(localizationController.getLocalizedText("transformations.rotateClockwise"));
        bRotateAC.setText(localizationController.getLocalizedText("transformations.rotateAntiClockwise"));
        bNegative.setText(localizationController.getLocalizedText("transformations.negative"));
    }

    @Override
    public void initialize(EventHandler eventHandler) {
        update();
        ImageController imageController = ImageController.getInstance();
        TransformationPipelineController pipelineController = TransformationPipelineController.getInstance();
        PipelineBarViewFxml pipelineView = PipelineBarViewFxml.getInstance();

        setButtonsDisabled(true);

        if (eventHandler instanceof ImageEventHandler) {
            ((ImageEventHandler) eventHandler).addOnImageLoadedListener(() -> setButtonsDisabled(false));
        }

        bFlipUpDown.setOnAction(e -> addTransformationToPipeline(new FlipUpsideDownTransformation(), pipelineController, pipelineView));
        bFlipSide.setOnAction(e -> addTransformationToPipeline(new FlipSideToSideTransformation(), pipelineController, pipelineView));
        bRotateC.setOnAction(e -> addTransformationToPipeline(new Rotate90ClockwiseTransformation(), pipelineController, pipelineView));
        bRotateAC.setOnAction(e -> addTransformationToPipeline(new Rotate90AntiClockwiseTransformation(), pipelineController, pipelineView));
        bNegative.setOnAction(e -> addTransformationToPipeline(new NegativeTransformation(), pipelineController, pipelineView));
    }

    private void setButtonsDisabled(boolean disabled) {
        bFlipUpDown.setDisable(disabled);
        bFlipSide.setDisable(disabled);
        bRotateC.setDisable(disabled);
        bRotateAC.setDisable(disabled);
        bNegative.setDisable(disabled);
    }

    private void addTransformationToPipeline(ImageTransformationStrategy strategy, TransformationPipelineController pipelineController, PipelineBarViewFxml pipelineView) {
        pipelineController.addTransformation(strategy);
        pipelineView.updatePipelineTextArea();
        StateController.getInstance().setUnsavedChanges(true);

        String transformationKey = transformationKeys.getOrDefault(strategy.getClass(), "transformation.unknown");
        String transformationName = LocalizationController.getInstance().getLocalizedText(transformationKey);

        String logMessage = LocalizationController.getInstance().getLocalizedText("transformation.added")
                .replace("{transformation}", transformationName);

        LogBarViewFxml.getInstance().addLogEntry(logMessage);
    }
}
