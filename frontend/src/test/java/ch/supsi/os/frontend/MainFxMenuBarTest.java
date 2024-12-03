package ch.supsi.os.frontend;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.hamcrest.Matchers.notNullValue;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;


public class MainFxMenuBarTest extends AbstractMainGUITest {

    @Test
    void testMenuBarIsLoaded() {
        step("Verifica che la MenuBar sia caricata", () -> {
            MenuBar menuBar = lookup("#menuBar").queryAs(MenuBar.class);
            verifyThat(menuBar, notNullValue());
        });
    }

    @Test
    void testMenuTexts() {
        step("Verifica i testi dei menu principali", () -> {
            verifyThat("#menuFile", hasText("File"));
            verifyThat("#menuEdit", hasText("Edit"));
            verifyThat("#menuHelp", hasText("Help"));
        });
    }

    @Test
    void testMenuItemOpenAction() {
        step("Simula clic su 'Open' e verifica il comportamento", () -> {
            MenuBar menuBar = (MenuBar) lookup("#menuBar").query();
            assertNotNull(menuBar, "MenuBar non trovato!");

            Menu menuFile = menuBar.getMenus().stream()
                    .filter(menu -> "File".equals(menu.getText()))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("Menu 'File' non trovato!"));

            MenuItem menuItemOpen = menuFile.getItems().stream()
                    .filter(item -> "Open".equals(item.getText()))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("MenuItem 'Open' non trovato!"));

            menuItemOpen.fire();
            System.out.println("Azione 'Open' eseguita con successo.");
        });
    }

    @Test
    void testMenuItemSaveAsAction() {
        step("Simula clic su 'Save As' e verifica il comportamento", () -> {
            MenuBar menuBar = (MenuBar) lookup("#menuBar").query();
            assertNotNull(menuBar, "MenuBar non trovato!");

            Menu menuFile = menuBar.getMenus().stream()
                    .filter(menu -> "File".equals(menu.getText()))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("Menu 'File' non trovato!"));

            MenuItem menuItemSaveAs = menuFile.getItems().stream()
                    .filter(item -> "Save As".equals(item.getText()))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("MenuItem 'Save As' non trovato!"));

            menuItemSaveAs.fire();
            System.out.println("Azione 'Save As' eseguita con successo.");
        });
    }

    @Test
    void testMenuItemPreferencesAction() {
        step("Simula clic su 'Preferences' e verifica il comportamento", () -> {
            MenuBar menuBar = (MenuBar) lookup("#menuBar").query();
            assertNotNull(menuBar, "MenuBar non trovato!");

            Menu menuEdit = menuBar.getMenus().stream()
                    .filter(menu -> "Edit".equals(menu.getText()))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("Menu 'Edit' non trovato!"));

            MenuItem menuItemPreferences = menuEdit.getItems().stream()
                    .filter(item -> "Preferences".equals(item.getText()))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("MenuItem 'Preferences' non trovato!"));

            menuItemPreferences.fire();
            System.out.println("Azione 'Preferences' eseguita con successo.");
        });
    }

    @Test
    void testMenuItemAboutAction() {
        step("Simula clic su 'About' e verifica il comportamento", () -> {
            MenuBar menuBar = (MenuBar) lookup("#menuBar").query();
            assertNotNull(menuBar, "MenuBar non trovato!");

            Menu menuHelp = menuBar.getMenus().stream()
                    .filter(menu -> "Help".equals(menu.getText()))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("Menu 'Help' non trovato!"));

            MenuItem menuItemAbout = menuHelp.getItems().stream()
                    .filter(item -> "About".equals(item.getText()))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("MenuItem 'About' non trovato!"));

            menuItemAbout.fire();
            System.out.println("Azione 'About' eseguita con successo.");
        });
    }
}
