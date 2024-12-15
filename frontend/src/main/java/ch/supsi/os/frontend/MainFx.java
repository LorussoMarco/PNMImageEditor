package ch.supsi.os.frontend;

import ch.supsi.os.frontend.controller.EventHandler;
import ch.supsi.os.frontend.controller.ImageEventHandler;
import ch.supsi.os.frontend.controller.LocalizationController;
import ch.supsi.os.frontend.view.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.MenuItem;


public class MainFx extends Application {
    private final ImageEventHandler eventHandler;

    private final ControlledFxView menuBarView;
    private final ControlledFxView transformationsView;
    private final ControlledFxView imageView;
    private final ControlledFxView pipelineView;
    private final ControlledFxView logbarView;

    public MainFx() {
        this.eventHandler = ImageEventHandler.getInstance();
        this.logbarView = LogBarViewFxml.getInstance();
        this.menuBarView = MenuBarViewFxml.getInstance();
        this.transformationsView = TransformationsViewFxml.getInstance();
        this.imageView = ImageViewFxml.getInstance();
        this.pipelineView = PipelineBarViewFxml.getInstance();
    }

    @Override
    public void start(Stage stage){
        LocalizationController localizationController = LocalizationController.getInstance();

        localizationController.initializeLocale();
        localizationController.registerView(menuBarView);
        localizationController.registerView(transformationsView);
        localizationController.registerView(imageView);
        localizationController.registerView(pipelineView);
        localizationController.registerView(logbarView);

        BorderPane root = new BorderPane();
        root.setTop(this.menuBarView.getNode());
        root.setLeft(this.transformationsView.getNode());
        root.setCenter(this.imageView.getNode());
        root.setRight(this.pipelineView.getNode());
        root.setBottom(this.logbarView.getNode());

        eventHandler.setPrimaryStage(stage);

        menuBarView.initialize(eventHandler);
        imageView.initialize(eventHandler);
        transformationsView.initialize(eventHandler);
        pipelineView.initialize(eventHandler);
        logbarView.initialize(eventHandler);

        Scene scene = new Scene(root);

        stage.setTitle(localizationController.getLocalizedText("app.title"));
        stage.setResizable(true);
        stage.setMinWidth(500);
        stage.setMinHeight(500);
        stage.toFront();
        stage.setScene(scene);
        stage.show();


        LogBarViewFxml.getInstance().addLogEntry(localizationController.getLocalizedText("app.start.log"));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
