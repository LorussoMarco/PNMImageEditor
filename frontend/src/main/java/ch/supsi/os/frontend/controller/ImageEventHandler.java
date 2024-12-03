package ch.supsi.os.frontend.controller;

import ch.supsi.os.backend.application.ImageController;
import ch.supsi.os.backend.business.ImageModel;
import ch.supsi.os.frontend.view.ImageViewFxml;
import ch.supsi.os.frontend.view.LogBarViewFxml;
import javafx.application.Platform;
import javafx.scene.control.Alert;
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
        Platform.runLater(() -> {
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
                    notifyImageLoadedListeners();
                } catch (IOException e) {
                    handleFileError(e, selectedFile.getAbsolutePath());
                }
            }
        });
    }

    /**
     * Gestisce gli errori durante l'apertura del file.
     */
    private void handleFileError(IOException e, String filePath) {
        if (e.getMessage().contains("Unsupported image format")) {
            showAlert("error.title", "error.header", "error.unsupportedFormat", filePath);
        } else if (e.getMessage().contains("Error reading file")) {
            showAlert("error.title", "error.header", "error.readFile", filePath);
        } else {
            showAlert("error.title", "error.header", "error.unexpected", filePath);
        }
    }


    private void showAlert(String keyTitle, String keyHeader, String keyContent, String... contentParams) {
        LocalizationController localizationController = LocalizationController.getInstance();

        String title = localizationController.getLocalizedText(keyTitle);
        String header = localizationController.getLocalizedText(keyHeader);
        String content = localizationController.getLocalizedText(keyContent);

        if (contentParams != null && contentParams.length > 0) {
            content = java.text.MessageFormat.format(content, (Object[]) contentParams);
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void handleSaveMenuItem() {
        ImageController controller = ImageController.getInstance();
        String currentPath = controller.getCurrentImagePath();
        if (currentPath == null || currentPath.isEmpty()) {
            LogBarViewFxml.getInstance().addLogEntry("No image path available to overwrite.");
            return;
        }

        try {
            controller.saveImageToFile(currentPath);
            LogBarViewFxml.getInstance().addLogEntry("Image saved: " + currentPath);
        } catch (IOException e) {
            LogBarViewFxml.getInstance().addLogEntry("Error saving image: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void handleSaveAsMenuItem() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image As");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNM files (*.pbm)", "*.pbm"),
                new FileChooser.ExtensionFilter("PNM files (*.pgm)", "*.pgm"),
                new FileChooser.ExtensionFilter("PNM files (*.ppm)", "*.ppm")
        );

        File selectedFile = fileChooser.showSaveDialog(primaryStage);
        if (selectedFile != null) {
            String extension = getFileExtension(selectedFile.getName());
            String magicNumber = mapExtensionToMagicNumber(extension);

            if (magicNumber == null) {
                LogBarViewFxml.getInstance().addLogEntry("Unsupported file extension: " + extension);
                return;
            }

            try {
                ImageController.getInstance().saveImageAs(selectedFile.getAbsolutePath(), magicNumber);
                LogBarViewFxml.getInstance().addLogEntry("Image saved as: " + selectedFile.getAbsolutePath());
            } catch (IOException e) {
                LogBarViewFxml.getInstance().addLogEntry("Error saving image as: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private String getFileExtension(String fileName) {
        int lastDot = fileName.lastIndexOf('.');
        return lastDot == -1 ? null : fileName.substring(lastDot + 1).toLowerCase();
    }

    private String mapExtensionToMagicNumber(String extension) {
        return switch (extension) {
            case "pbm" -> "P1";
            case "pgm" -> "P2";
            case "ppm" -> "P3";
            default -> null;
        };
    }

}
