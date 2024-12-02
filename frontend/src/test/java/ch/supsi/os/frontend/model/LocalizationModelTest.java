package ch.supsi.os.frontend.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class LocalizationModelTest {

    private LocalizationModel localizationModel;
    private final Path prefsFilePath = Paths.get(System.getProperty("user.home"), "user_preferences.txt");

    @BeforeEach
    void setUp() throws IOException {
        localizationModel = new LocalizationModel();
        if (Files.exists(prefsFilePath)) {
            Files.delete(prefsFilePath);
        }
        Files.createFile(prefsFilePath);
    }

    @AfterEach
    void tearDown() throws IOException {
        if (Files.exists(prefsFilePath)) {
            Files.delete(prefsFilePath);
        }
    }

    @Test
    void testGetLocale() {
        assertEquals(Locale.ENGLISH, localizationModel.getLocale());

        localizationModel.setLocale(Locale.ITALIAN);
        assertEquals(Locale.ITALIAN, localizationModel.getLocale());
    }

    @Test
    void testSetLocale() {
        localizationModel.setLocale(Locale.ITALIAN);
        assertEquals(Locale.ITALIAN, localizationModel.getLocale());

        localizationModel.setLocale(Locale.FRENCH);
        assertEquals(Locale.FRENCH, localizationModel.getLocale());
    }

    @Test
    void testLoadPreferenceExistingFile() throws IOException {
        Properties properties = new Properties();
        properties.setProperty("language", "it");
        try (var writer = Files.newBufferedWriter(prefsFilePath)) {
            properties.store(writer, null);
        }

        String loadedPreference = LocalizationModel.loadPreference("language", "en");
        assertEquals("it", loadedPreference);
    }

    @Test
    void testLoadPreferenceDefaultValue() {
        String loadedPreference = LocalizationModel.loadPreference("nonexistentKey", "en");
        assertEquals("en", loadedPreference);
    }

    @Test
    void testLoadPreferenceInvalidFile() throws IOException {
        Files.delete(prefsFilePath);

        String loadedPreference = LocalizationModel.loadPreference("language", "en");
        assertEquals("en", loadedPreference);
    }
}
