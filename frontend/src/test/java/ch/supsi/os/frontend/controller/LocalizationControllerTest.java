package ch.supsi.os.frontend.controller;

import ch.supsi.os.frontend.model.LocalizationModel;
import ch.supsi.os.frontend.view.ControlledFxView;
import javafx.scene.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class LocalizationControllerTest {

    private LocalizationController controller;
    private Path preferencesPath;

    @BeforeEach
    void setUp() {
        controller = LocalizationController.getInstance();
        preferencesPath = Path.of(System.getProperty("user.home"), "user_preferences.txt");

        // Reset preferences file before each test
        try {
            Files.deleteIfExists(preferencesPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetAvailableLanguages() {
        List<String> availableLanguages = controller.getAvailableLanguages();
        assertNotNull(availableLanguages, "La lista delle lingue disponibili non dovrebbe essere null");
        assertTrue(availableLanguages.contains("en"), "La lista dovrebbe contenere 'English'");
    }

    @Test
    void testSetLocale() {
        controller.setLocale(Locale.ITALIAN);
        Locale locale = getControllerLocale(controller);
        assertEquals(Locale.ITALIAN, locale, "Il locale dovrebbe essere impostato su ITALIAN");
    }

    @Test
    void testInitializeLocale() {
        // Simula una preferenza salvata come "en"
        LocalizationModel.loadPreference("language", "en");
        controller.initializeLocale();
        Locale locale = getControllerLocale(controller);
        assertEquals(Locale.ENGLISH, locale, "Il locale dovrebbe essere inizializzato come ENGLISH");
    }

    @Test
    void testGetLocalizedText() {
        String localizedText = controller.getLocalizedText("test.key");
        assertNotNull(localizedText, "Il testo localizzato non dovrebbe essere null");
        assertTrue(localizedText.startsWith("!") || localizedText.length() > 0,
                "Il testo localizzato dovrebbe avere un valore valido o iniziare con '!'");
    }

    @Test
    void testRegisterViewAndNotifyLocaleChanged() {
        ControlledFxView mockView = new MockControlledFxView();
        controller.registerView(mockView);
        controller.notifyLocaleChanged();

        assertTrue(((MockControlledFxView) mockView).isUpdated(),
                "Il metodo update dovrebbe essere chiamato sulla vista registrata");
    }

    /**
     * Helper per accedere al locale del controller.
     */
    private Locale getControllerLocale(LocalizationController controller) {
        try {
            var field = LocalizationController.class.getDeclaredField("localizationModel");
            field.setAccessible(true);
            LocalizationModel model = (LocalizationModel) field.get(controller);
            return model.getLocale();
        } catch (Exception e) {
            throw new RuntimeException("Impossibile accedere al locale del controller", e);
        }
    }

    private static class MockControlledFxView implements ControlledFxView {
        private boolean updated = false;

        @Override
        public void update() {
            updated = true;
        }

        public boolean isUpdated() {
            return updated;
        }

        @Override
        public Node getNode() {
            return null;
        }

        @Override
        public void initialize(EventHandler eventHandler) {

        }
    }
}
