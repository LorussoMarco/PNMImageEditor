package ch.supsi.os.frontend;

import ch.supsi.os.frontend.controller.ImageEventHandler;
import ch.supsi.os.frontend.view.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainFx extends Application {
    public static final String APPLICATION_TITLE = "2D Image Editor";

    private final ControlledFxView menuBarView;
    private final ControlledFxView transformationsView;
    private final ControlledFxView imageView;
    private final ControlledFxView pipelineView;

    public MainFx() {
        this.menuBarView = MenuBarViewFxml.getInstance();
        this.transformationsView = TransformationsViewFxml.getInstance();
        this.imageView = ImageViewFxml.getInstance();
        this.pipelineView = PipelineBarViewFxml.getInstance();
    }

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();
        root.setTop(this.menuBarView.getNode());
        root.setLeft(this.transformationsView.getNode());
        root.setCenter(this.imageView.getNode());
        root.setRight(this.pipelineView.getNode());

        ImageEventHandler eventHandler = new ImageEventHandler(stage);

        menuBarView.initialize(eventHandler);
        imageView.initialize(eventHandler);
        transformationsView.initialize(eventHandler);
        pipelineView.initialize(eventHandler);

        Scene scene = new Scene(root);

        stage.setTitle(APPLICATION_TITLE);
        stage.setResizable(true);

        stage.toFront();
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
