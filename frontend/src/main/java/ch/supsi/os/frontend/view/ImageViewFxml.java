package ch.supsi.os.frontend.view;

import ch.supsi.os.backend.business.ImageModel;
import ch.supsi.os.frontend.controller.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;

public class ImageViewFxml implements ControlledFxView {

    private static ImageViewFxml myself;

    @FXML
    private ImageView imageView;

    @FXML
    private BorderPane imageBorderPane;

    public static ImageViewFxml getInstance() {
        if (myself == null) {
            myself = new ImageViewFxml();

            try {
                URL fxmlUrl = ImageViewFxml.class.getResource("/imageview.fxml");
                if (fxmlUrl != null) {
                    FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
                    fxmlLoader.setController(myself);
                    fxmlLoader.load();
                } else {
                    throw new RuntimeException("FXML file 'imageview.fxml' not found");
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to load FXML file 'imageview.fxml'", e);
            }
        }
        return myself;
    }

    private ImageViewFxml() {}

    public void drawImage(ImageModel imageModel) {
        WritableImage writableImage = createWritableImageFromModel(imageModel);
        imageView.setImage(writableImage);
    }

    private WritableImage createWritableImageFromModel(ImageModel imageModel) {
        int width = imageModel.getWidth();
        int height = imageModel.getHeight();

        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Invalid image dimensions: width and height must be positive.");
        }

        int[][] pixels = imageModel.getPixels();
        int channels = imageModel.getChannels(); // Check for grayscale or RGB
        WritableImage writableImage = new WritableImage(width, height);

        PixelWriter pixelWriter = writableImage.getPixelWriter();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (channels == 3) { // Color image (RGB)
                    int index = x * 3;
                    int r = pixels[y][index];
                    int g = pixels[y][index + 1];
                    int b = pixels[y][index + 2];
                    Color color = Color.rgb(r, g, b);
                    pixelWriter.setColor(x, y, color);
                } else { // Grayscale image
                    int gray = pixels[y][x];
                    Color color = Color.grayRgb(gray);
                    pixelWriter.setColor(x, y, color);
                }
            }
        }

        return writableImage;
    }


    @Override
    public Node getNode() {
        return imageBorderPane;
    }

    @Override
    public void update() {
    }

    @Override
    public void initialize(EventHandler eventHandler) {

    }
}
