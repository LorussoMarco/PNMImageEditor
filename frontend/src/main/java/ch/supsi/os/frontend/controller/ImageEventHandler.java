package ch.supsi.os.frontend.controller;

import ch.supsi.os.backend.application.ImageController;
import ch.supsi.os.backend.model.ImageModel;
import ch.supsi.os.frontend.view.ImageViewFxml;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ImageEventHandler implements EventHandler {

    private Stage primaryStage;

    public ImageEventHandler(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void handleOpenMenuItem() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open PNM Image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNM files", "*.pbm", "*.pgm", "*.ppm"));

        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            try {
                System.out.println("File selected: " + selectedFile.getAbsolutePath());
                ImageController.getInstance().loadImageFromFile(selectedFile.getAbsolutePath());
                ImageModel imageModel = ImageController.getInstance().getImageModel();
                ImageViewFxml.getInstance().drawImage(imageModel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
