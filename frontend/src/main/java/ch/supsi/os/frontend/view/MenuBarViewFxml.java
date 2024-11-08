package ch.supsi.os.frontend.view;

import ch.supsi.os.frontend.controller.EventHandler;
import ch.supsi.os.frontend.controller.ImageEventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import java.io.IOException;
import java.net.URL;


public class MenuBarViewFxml implements ControlledFxView {

    private static MenuBarViewFxml myself;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Menu menuFile;

    @FXML
    private Menu menuEdit;

    @FXML
    private Menu menuHelp;

    @FXML
    private MenuItem menuItemOpen;

    @FXML
    private MenuItem menuItemExport;

    @FXML
    private MenuItem menuItemSaveAs;

    @FXML
    private MenuItem menuItemPreferences;

    @FXML
    private MenuItem menuItemAbout;

    private MenuBarViewFxml() {}

    public static MenuBarViewFxml getInstance() {
        if (myself == null) {
            myself = new MenuBarViewFxml();

            try{
                URL fxmlUrl = MenuBarViewFxml.class.getResource("/menubar.fxml");
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
    public Node getNode() {

        return this.menuBar;
    }

    @Override
    public void update() {

    }

    @Override
    public void initialize(EventHandler eventHandler) {
        ImageEventHandler handler = (ImageEventHandler) eventHandler;
        menuItemOpen.setOnAction(e -> handler.handleOpenMenuItem());
        menuItemSaveAs.setOnAction(e -> handler.handleSaveMenuItem());
        menuItemAbout.setOnAction(e -> AboutView.getInstance().showAboutDialog());
        menuItemPreferences.setOnAction(e -> PreferencesView.showPreferencesDialog());
    }
}