package ch.supsi.os.frontend.view;

import ch.supsi.os.frontend.controller.EventHandler;
import ch.supsi.os.frontend.controller.ImageEventHandler;
import ch.supsi.os.frontend.controller.LocalizationController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import java.io.IOException;

public class MenuBarViewFxml implements ControlledFxView {

    private static MenuBarViewFxml myself;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Menu menuFile;

    @FXML
    private MenuItem menuItemOpen;
    @FXML
    private MenuItem menuItemExport;

    @FXML
    private MenuItem menuItemSaveAs;

    @FXML
    private Menu menuEdit;

    @FXML
    private MenuItem menuItemPreferences;

    @FXML
    private Menu menuHelp;

    @FXML
    private MenuItem menuItemAbout;

    private MenuBarViewFxml() {}

    public static MenuBarViewFxml getInstance() {
        if (myself == null) {
            myself = new MenuBarViewFxml();
            try {
                FXMLLoader loader = new FXMLLoader(MenuBarViewFxml.class.getResource("/menubar.fxml"));
                loader.setController(myself);
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return myself;
    }

    public Node getNode() {
        return menuBar;
    }

    @Override
    public void update() {
        LocalizationController localizationController = LocalizationController.getInstance();
        menuFile.setText(localizationController.getLocalizedText("menu.file"));
        menuItemOpen.setText(localizationController.getLocalizedText("menu.file.open"));
        menuItemExport.setText(localizationController.getLocalizedText("menu.file.export"));
        menuItemSaveAs.setText(localizationController.getLocalizedText("menu.file.saveAs"));
        menuEdit.setText(localizationController.getLocalizedText("menu.edit"));
        menuItemPreferences.setText(localizationController.getLocalizedText("menu.edit.preferences"));
        menuHelp.setText(localizationController.getLocalizedText("menu.help"));
        menuItemAbout.setText(localizationController.getLocalizedText("menu.help.about"));
    }


    @Override
    public void initialize(EventHandler eventHandler) {
        update();
        ImageEventHandler handler = (ImageEventHandler) eventHandler;
        menuItemOpen.setOnAction(e -> handler.handleOpenMenuItem());
        menuItemSaveAs.setOnAction(e -> handler.handleSaveMenuItem());
        menuItemAbout.setOnAction(e -> AboutView.getInstance().showAboutDialog());
        menuItemPreferences.setOnAction(e -> PreferencesView.showPreferencesDialog());
    }
}
