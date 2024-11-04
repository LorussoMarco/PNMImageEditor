package ch.supsi.os.frontend.controller;

import ch.supsi.os.backend.application.ImageController;
import ch.supsi.os.backend.business.ImageModel;
import ch.supsi.os.frontend.view.ImageViewFxml;
import ch.supsi.os.frontend.view.LogBarViewFxml;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ImageEventHandler implements EventHandler {

    private Stage primaryStage;
    private Runnable onImageLoaded;


    public ImageEventHandler(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setOnImageLoaded(Runnable onImageLoaded) {
        this.onImageLoaded = onImageLoaded;
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
                LogBarViewFxml.getInstance().addLogEntry("File opened successfully: " + selectedFile.getAbsolutePath());
                if (onImageLoaded != null) {
                    onImageLoaded.run();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleSaveMenuItem() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNM files", "*.pbm", "*.pgm", "*.ppm"));

        File selectedFile = fileChooser.showSaveDialog(primaryStage);
        if (selectedFile != null) {
            try {
                ImageController.getInstance().saveImageToFile(selectedFile.getAbsolutePath());
                LogBarViewFxml.getInstance().addLogEntry("File saved successfully: " + selectedFile.getAbsolutePath());
                System.out.println("Image saved to " + selectedFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
