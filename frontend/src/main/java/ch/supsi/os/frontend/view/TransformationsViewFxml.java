package ch.supsi.os.frontend.view;

import ch.supsi.os.backend.application.ImageController;
import ch.supsi.os.backend.business.*;
import ch.supsi.os.frontend.controller.EventHandler;
import ch.supsi.os.frontend.controller.TransformationPipelineController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;

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


    private TransformationsViewFxml() {}

    public static TransformationsViewFxml getInstance() {
        if (myself == null) {
            myself = new TransformationsViewFxml();
            try{
                URL fxmlUrl = TransformationsViewFxml.class.getResource("/transformationsbar.fxml");
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
    public void update() {
    }

    @Override
    public Node getNode() {
        return transformationsBar;
    }

    @Override
    public void initialize(EventHandler eventHandler) {
        ImageViewFxml imageViewFxml = ImageViewFxml.getInstance();
        ImageController imageController = ImageController.getInstance();

        bFlipUpDown.setOnAction(e -> applyTransformation(new FlipUpsideDownTransformation(), imageController, imageViewFxml));
        bFlipSide.setOnAction(e -> applyTransformation(new FlipSideToSideTransformation(), imageController, imageViewFxml));
        bRotateC.setOnAction(e -> applyTransformation(new Rotate90ClockwiseTransformation(), imageController, imageViewFxml));
        bRotateAC.setOnAction(e -> applyTransformation(new Rotate90AntiClockwiseTransformation(), imageController, imageViewFxml));
        bNegative.setOnAction(e -> applyTransformation(new NegativeTransformation(), imageController, imageViewFxml));

        TransformationPipelineController pipelineController = TransformationPipelineController.getInstance();

        bFlipUpDown.setOnAction(e -> addTransformationToPipeline(new FlipUpsideDownTransformation(), pipelineController));
        bFlipSide.setOnAction(e -> addTransformationToPipeline(new FlipSideToSideTransformation(), pipelineController));
        bRotateC.setOnAction(e -> addTransformationToPipeline(new Rotate90ClockwiseTransformation(), pipelineController));
        bRotateAC.setOnAction(e -> addTransformationToPipeline(new Rotate90AntiClockwiseTransformation(), pipelineController));
        bNegative.setOnAction(e -> addTransformationToPipeline(new NegativeTransformation(), pipelineController));
    }

    private void addTransformationToPipeline(ImageTransformationStrategy strategy, TransformationPipelineController pipelineController) {
        pipelineController.addTransformation(strategy);
        PipelineBarViewFxml.getInstance().updatePipelineTextArea();
    }

    private void applyTransformation(ImageTransformationStrategy strategy, ImageController imageController, ImageViewFxml imageViewFxml) {
        ImageModel imageModel = imageController.getImageModel();
        if (imageModel != null) {
            strategy.applyTransformation(imageModel);
            imageViewFxml.drawImage(imageModel);
        } else {
            System.out.println("No image loaded for transformation.");
        }
    }

}
