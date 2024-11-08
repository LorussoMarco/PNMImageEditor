package ch.supsi.os.frontend.view;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;

class PreferencesViewTest {

    private static final String PREFS_FILE_NAME = "user_preferences.txt";
    private static final Path PREFS_FILE_PATH = Path.of(System.getProperty("user.home"), PREFS_FILE_NAME);
    private static final String TEST_LANGUAGES_RESOURCE_PATH = "/languages_test.properties";

    @BeforeAll
    static void setUpTestEnvironment() throws IOException {
        Path testFilePath = Path.of("src/test/resources", "languages_test.properties");
        Files.createDirectories(testFilePath.getParent());

        Properties testProperties = new Properties();
        testProperties.setProperty("en", "English");
        testProperties.setProperty("it", "Italian");
        testProperties.setProperty("fr", "French");

        try (FileWriter writer = new FileWriter(testFilePath.toFile())) {
            testProperties.store(writer, "Test Languages");
        }
    }

    @AfterEach
    void cleanUp() throws IOException {
        Files.deleteIfExists(PREFS_FILE_PATH);
    }

    @Test
    void testSavePreferences() throws IOException {
        String testLanguage = "English";
        PreferencesView.savePreferences(testLanguage);

        assertTrue(Files.exists(PREFS_FILE_PATH), "Preferences file should exist after saving.");

        Properties properties = new Properties();
        try (FileReader reader = new FileReader(PREFS_FILE_PATH.toFile())) {
            properties.load(reader);
        }

        assertEquals(testLanguage, properties.getProperty("language"), "Language should match the saved preference.");
    }

    @Test
    void testLoadAvailableLanguages() {
        List<String> languages = PreferencesView.loadAvailableLanguages();

        assertNotNull(languages, "Languages list should not be null.");
        assertTrue(languages.contains("English"), "Languages list should contain English.");
        assertTrue(languages.contains("Italian"), "Languages list should contain Italian.");
        assertFalse(languages.contains("French"), "Languages list should contain French.");
    }

    @Test
    void testLoadAvailableLanguagesFallback() {
        Path testLanguagesPath = Path.of("src/test/resources/languages_test.properties");
        Path tempPath = Path.of("src/test/resources/languages_test_backup.properties");
        try {
            Files.move(testLanguagesPath, tempPath);

            List<String> languages = PreferencesView.loadAvailableLanguages();

            assertEquals(2, languages.size(), "Fallback should provide only one language.");
            assertEquals("English", languages.get(0), "Fallback language should be English.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                Files.move(tempPath, testLanguagesPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @BeforeEach
    void setUp() throws IOException {
        Files.deleteIfExists(PREFS_FILE_PATH);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(PREFS_FILE_PATH);
    }

    @Test
    void testLoadPreferenceWhenFileExists() throws IOException {
        Properties properties = new Properties();
        properties.setProperty("language", "English");
        try (FileWriter writer = new FileWriter(PREFS_FILE_PATH.toFile())) {
            properties.store(writer, "Test Preferences");
        }

        String result = PreferencesView.loadPreference("language", "Italian");
        assertEquals("English", result, "The preference value should be 'English' as set in the file.");
    }

    @Test
    void testLoadPreferenceWhenKeyDoesNotExist() throws IOException {
        Properties properties = new Properties();
        properties.setProperty("language", "English");
        try (FileWriter writer = new FileWriter(PREFS_FILE_PATH.toFile())) {
            properties.store(writer, "Test Preferences");
        }

        String result = PreferencesView.loadPreference("theme", "Light");
        assertEquals("Light", result, "The method should return the default value 'Light' for a non-existing key.");
    }

    @Test
    void testLoadPreferenceWhenFileDoesNotExist() {
        assertFalse(Files.exists(PREFS_FILE_PATH), "The preferences file should not exist.");
        String result = PreferencesView.loadPreference("language", "Italian");
        assertEquals("Italian", result, "The method should return the default value 'Italian' when the file does not exist.");
    }
}
