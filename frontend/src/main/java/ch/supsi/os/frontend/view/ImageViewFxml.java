package ch.supsi.os.frontend.view;

import ch.supsi.os.frontend.controller.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.File;
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


    // This method is not used in the code
    /*public void loadImage(String imagePath) {
        File file = new File(imagePath);
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
    }*/

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
