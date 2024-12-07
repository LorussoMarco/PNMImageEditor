package ch.supsi.os.frontend.view;

import ch.supsi.os.backend.application.ImageController;
import ch.supsi.os.frontend.controller.EventHandler;
import ch.supsi.os.frontend.controller.ImageEventHandler;
import ch.supsi.os.frontend.controller.LocalizationController;
import ch.supsi.os.frontend.controller.StateController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;

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
    private MenuItem menuItemSave;

    @FXML
    private MenuItem menuItemSaveAs;
    @FXML
    private MenuItem menuItemQuit;

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
        menuItemSave.setText(localizationController.getLocalizedText("menu.file.export"));
        menuItemSaveAs.setText(localizationController.getLocalizedText("menu.file.saveAs"));
        menuEdit.setText(localizationController.getLocalizedText("menu.edit"));
        menuItemPreferences.setText(localizationController.getLocalizedText("menu.edit.preferences"));
        menuHelp.setText(localizationController.getLocalizedText("menu.help"));
        menuItemAbout.setText(localizationController.getLocalizedText("menu.help.about"));
        menuItemQuit.setText(localizationController.getLocalizedText("menu.file.quit"));
    }


    @Override
    public void initialize(EventHandler eventHandler) {
        update();
        ImageEventHandler handler = (ImageEventHandler) eventHandler;
        menuItemOpen.setOnAction(e -> handler.handleOpenMenuItem());
        menuItemSave.setOnAction(e -> handler.handleSaveMenuItem());
        menuItemSaveAs.setOnAction(e -> handler.handleSaveAsMenuItem());
        menuItemAbout.setOnAction(e -> AboutView.getInstance().showAboutDialog());
        menuItemPreferences.setOnAction(e -> PreferencesView.showPreferencesDialog());
        menuItemQuit.setOnAction(event -> handleQuitMenuItem());

    }

    private void handleQuitMenuItem() {
        StateController stateController = StateController.getInstance();
        LocalizationController localizationController = LocalizationController.getInstance();

        if (stateController.hasUnsavedChanges()) {
            Alert unsavedAlert = new Alert(Alert.AlertType.CONFIRMATION);
            unsavedAlert.setTitle(localizationController.getLocalizedText("quit.unsaved.title"));
            unsavedAlert.setHeaderText(localizationController.getLocalizedText("quit.unsaved.header"));
            unsavedAlert.setContentText(localizationController.getLocalizedText("quit.unsaved.content"));

            ButtonType saveButton = new ButtonType(localizationController.getLocalizedText("quit.unsaved.save"));
            ButtonType quitButton = new ButtonType(localizationController.getLocalizedText("quit.unsaved.quit"));
            ButtonType cancelButton = new ButtonType(localizationController.getLocalizedText("quit.unsaved.cancel"), ButtonBar.ButtonData.CANCEL_CLOSE);

            unsavedAlert.getButtonTypes().setAll(saveButton, quitButton, cancelButton);

            unsavedAlert.showAndWait().ifPresent(result -> {
                if (result == saveButton) {
                    try {
                        saveAllChanges();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Platform.exit();
                } else if (result == quitButton) {
                    Platform.exit();
                }
            });
        } else {
            Platform.exit();
        }
    }

    private void saveAllChanges() throws IOException {
        ImageController.getInstance().saveImageToFile(ImageController.getInstance().getCurrentImagePath());
        StateController.getInstance().setUnsavedChanges(false);
    }
}
