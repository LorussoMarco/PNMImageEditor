package ch.supsi.os.frontend.controller;

import ch.supsi.os.backend.application.ImageController;
import ch.supsi.os.backend.business.ImageModel;
import ch.supsi.os.frontend.view.ImageViewFxml;
import ch.supsi.os.frontend.view.LogBarViewFxml;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageEventHandler implements EventHandler {

    private Stage primaryStage;
    private final List<Runnable> onImageLoadedListeners = new ArrayList<>();

    public ImageEventHandler(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void addOnImageLoadedListener(Runnable listener) {
        onImageLoadedListeners.add(listener);
    }

    private void notifyImageLoadedListeners() {
        for (Runnable listener : onImageLoadedListeners) {
            listener.run();
        }
    }

    public void handleOpenMenuItem() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(LocalizationController.getInstance().getLocalizedText("menu.file.open"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNM files", "*.pbm", "*.pgm", "*.ppm"));

        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            try {
                System.out.println("File selected: " + selectedFile.getAbsolutePath());
                ImageController.getInstance().loadImageFromFile(selectedFile.getAbsolutePath());
                ImageModel imageModel = ImageController.getInstance().getImageModel();
                ImageViewFxml.getInstance().drawImage(imageModel);
                LogBarViewFxml.getInstance().addLogEntry(
                        LocalizationController.getInstance().getLocalizedText("log.image.opened")
                                .replace("{file}", selectedFile.getAbsolutePath())
                );
                notifyImageLoadedListeners(); // Notifica tutti i listener
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleSaveMenuItem() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(LocalizationController.getInstance().getLocalizedText("menu.file.saveAs"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNM files", "*.pbm", "*.pgm", "*.ppm"));

        File selectedFile = fileChooser.showSaveDialog(primaryStage);
        if (selectedFile != null) {
            try {
                ImageController.getInstance().saveImageToFile(selectedFile.getAbsolutePath());
                LogBarViewFxml.getInstance().addLogEntry(
                        LocalizationController.getInstance().getLocalizedText("log.image.saved")
                                .replace("{file}", selectedFile.getAbsolutePath())
                );
                System.out.println("Image saved to " + selectedFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
