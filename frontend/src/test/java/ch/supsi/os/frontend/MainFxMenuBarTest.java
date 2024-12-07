package ch.supsi.os.frontend;

import ch.supsi.os.frontend.controller.ImageEventHandler;
import ch.supsi.os.frontend.controller.LocalizationController;
import com.sun.javafx.scene.control.ContextMenuContent;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.TextInputControlMatchers;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Objects;

import static org.mockito.Mockito.when;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;


public class MainFxMenuBarTest extends AbstractMainGUITest {

    @Test
    public void walkThrough() {
        testFileMenu();
        testEditMenu();
        testHelpMenu();
    }

    private void testFileMenu() {
        step("file menu", () -> {
            // open menu
            sleep(SLEEP_INTERVAL);
            clickOn("#menuFile");
            sleep(SLEEP_INTERVAL);

            MenuItem menuItemOpen = lookup("#menuItemOpen").queryAs(ContextMenuContent.MenuItemContainer.class).getItem();
            Assertions.assertTrue(menuItemOpen.isVisible());
            Assertions.assertFalse(menuItemOpen.isDisable());

            MenuItem menuItemSave = lookup("#menuItemSave").queryAs(ContextMenuContent.MenuItemContainer.class).getItem();
            Assertions.assertTrue(menuItemSave.isVisible());
            Assertions.assertFalse(menuItemSave.isDisable());

            MenuItem menuItemSaveAs = lookup("#menuItemSaveAs").queryAs(ContextMenuContent.MenuItemContainer.class).getItem();
            Assertions.assertTrue(menuItemSaveAs.isVisible());
            Assertions.assertFalse(menuItemSaveAs.isDisable());

            MenuItem menuItemQuit = lookup("#menuItemQuit").queryAs(ContextMenuContent.MenuItemContainer.class).getItem();
            Assertions.assertTrue(menuItemQuit.isVisible());
            Assertions.assertFalse(menuItemQuit.isDisable());

            // close menu
            sleep(SLEEP_INTERVAL);
            clickOn("#menuFile");
        });
    }


    private void testEditMenu() {
        step("edit menu", () -> {
            // open menu
            sleep(SLEEP_INTERVAL);
            clickOn("#menuEdit");
            sleep(SLEEP_INTERVAL);

            MenuItem menuItemPreferences = lookup("#menuItemPreferences").queryAs(ContextMenuContent.MenuItemContainer.class).getItem();
            Assertions.assertTrue(menuItemPreferences.isVisible());
            Assertions.assertFalse(menuItemPreferences.isDisable());

            clickOn("#menuItemPreferences");

            verifyThat("#preferencesGridPane", NodeMatchers.isVisible());

            sleep(SLEEP_INTERVAL);

            clickOn("#preferencesSaveButton");

        });
    }

    private void testHelpMenu() {
        step("help menu", () -> {
            // open menu
            sleep(SLEEP_INTERVAL);
            clickOn("#menuHelp");
            sleep(SLEEP_INTERVAL);

            MenuItem menuItemAbout = lookup("#menuItemAbout").queryAs(ContextMenuContent.MenuItemContainer.class).getItem();
            Assertions.assertTrue(menuItemAbout.isVisible());
            Assertions.assertFalse(menuItemAbout.isDisable());

            sleep(SLEEP_INTERVAL);
            clickOn("#menuItemAbout");
            sleep(SLEEP_INTERVAL);
            verifyThat("#aboutBox", NodeMatchers.isVisible());
            clickOn("#closeButton");
        });
    }


}

