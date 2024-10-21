package ch.supsi.os.frontend.view;

import ch.supsi.os.backend.model.ImageModel;
import ch.supsi.os.frontend.controller.EventHandler;
import ch.supsi.os.frontend.controller.ImageEventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ImageViewFxml implements ControlledFxView {

    private static ImageViewFxml myself;

    @FXML
    private Canvas imageView;

    @FXML
    private BorderPane imageBorderPane;

    public static ImageViewFxml getInstance() {
        if (myself == null) {
            myself = new ImageViewFxml();

            try{
                URL fxmlUrl = TransformationsViewFxml.class.getResource("/imageview.fxml");
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

    private ImageViewFxml() {}

    /**
     * Renders the image on the Canvas using pixel data from the ImageModel.
     * Supports both grayscale and RGB images. Colors are set based on the
     * number of channels, and the image is scaled to fit the Canvas.
     */
    public void drawImage(ImageModel imageModel) {
        int width = imageModel.getWidth();
        int height = imageModel.getHeight();
        int[][] pixels = imageModel.getPixels();
        int channels = imageModel.getChannels();

        GraphicsContext gc = imageView.getGraphicsContext2D();
        gc.clearRect(0, 0, imageView.getWidth(), imageView.getHeight());

        double scaleX = imageView.getWidth() / width;
        double scaleY = imageView.getHeight() / height;
        double scale = Math.min(scaleX, scaleY);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (channels == 3) {
                    int index = x * 3;
                    int r = pixels[y][index];
                    int g = pixels[y][index + 1];
                    int b = pixels[y][index + 2];

                    Color color = Color.rgb(r, g, b);
                    gc.setFill(color);
                } else {
                    int gray = pixels[y][x];
                    Color color = Color.grayRgb(gray);
                    gc.setFill(color);
                }

                gc.fillRect(x * scale, y * scale, scale, scale);
            }
        }
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
