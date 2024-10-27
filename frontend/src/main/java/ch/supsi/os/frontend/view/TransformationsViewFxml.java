package ch.supsi.os.frontend.view;

import ch.supsi.os.backend.application.ImageController;
import ch.supsi.os.backend.model.ImageModel;
import ch.supsi.os.frontend.controller.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
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
        bFlipUpDown.setOnAction(e -> {
            ImageModel imageModel = ImageController.getInstance().getImageModel();
            if (imageModel != null) {
                imageModel.flipImageUpsideDown();
                ImageViewFxml imageViewFxml = ImageViewFxml.getInstance();
                imageViewFxml.drawImage(imageModel);
            } else {
                System.out.println("No image loaded to flip.");
            }
        });
        bFlipSide.setOnAction(e -> {
            ImageModel imageModel = ImageController.getInstance().getImageModel();
            if (imageModel != null) {
                imageModel.flipImageSideToSide();
                ImageViewFxml imageViewFxml = ImageViewFxml.getInstance();
                imageViewFxml.drawImage(imageModel);
            } else {
                System.out.println("No image loaded to flip.");
            }
        });

        bRotateC.setOnAction(e -> {
            ImageModel imageModel = ImageController.getInstance().getImageModel();
            if (imageModel != null) {
                imageModel.rotate90Clockwise();
                ImageViewFxml imageViewFxml = ImageViewFxml.getInstance();
                imageViewFxml.drawImage(imageModel);
            } else {
                System.out.println("No image loaded to rotate.");
            }
        });
    }
}
