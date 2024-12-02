package ch.supsi.os.frontend.view;

import org.junit.jupiter.api.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class PreferencesViewTest {

    private static final String PREFS_FILE_NAME = "user_preferences.txt";
    private static final Path PREFS_FILE_PATH = Path.of(System.getProperty("user.home"), PREFS_FILE_NAME);

    @BeforeEach
    void setUp() throws IOException {
        // Elimina eventuali file di preferenze esistenti prima di ogni test
        Files.deleteIfExists(PREFS_FILE_PATH);
    }

    @AfterEach
    void tearDown() throws IOException {
        // Pulisce i file di preferenze dopo ogni test
        Files.deleteIfExists(PREFS_FILE_PATH);
    }

    @Test
    void testSavePreferences() throws IOException {
        String testLanguage = "English";

        // Salva una preferenza
        PreferencesView.savePreferences(testLanguage);

        // Verifica che il file di preferenze sia stato creato
        assertTrue(Files.exists(PREFS_FILE_PATH), "Il file di preferenze dovrebbe esistere dopo il salvataggio.");

        // Leggi il contenuto del file di preferenze
        Properties properties = new Properties();
        try (FileReader reader = new FileReader(PREFS_FILE_PATH.toFile())) {
            properties.load(reader);
        }

        // Verifica che la preferenza salvata sia corretta
        assertEquals(testLanguage, properties.getProperty("language"), "La lingua dovrebbe corrispondere alla preferenza salvata.");
    }

    @Test
    void testLoadPreferenceWhenFileExists() throws IOException {
        // Crea un file di preferenze con una lingua preimpostata
        Properties properties = new Properties();
        properties.setProperty("language", "English");
        try (FileWriter writer = new FileWriter(PREFS_FILE_PATH.toFile())) {
            properties.store(writer, "Test Preferences");
        }

        // Verifica che la lingua venga caricata correttamente
        String result = PreferencesView.loadPreference("language", "Italian");
        assertEquals("English", result, "La lingua dovrebbe essere 'English' come impostata nel file.");
    }

    @Test
    void testLoadPreferenceWhenKeyDoesNotExist() throws IOException {
        // Crea un file di preferenze con una chiave diversa
        Properties properties = new Properties();
        properties.setProperty("language", "English");
        try (FileWriter writer = new FileWriter(PREFS_FILE_PATH.toFile())) {
            properties.store(writer, "Test Preferences");
        }

        // Verifica che venga restituito il valore di default per una chiave inesistente
        String result = PreferencesView.loadPreference("theme", "Light");
        assertEquals("Light", result, "Dovrebbe restituire il valore di default 'Light' per una chiave inesistente.");
    }

    @Test
    void testLoadPreferenceWhenFileDoesNotExist() {
        // Verifica che il file non esista
        assertFalse(Files.exists(PREFS_FILE_PATH), "Il file di preferenze non dovrebbe esistere.");

        // Verifica che venga restituito il valore di default
        String result = PreferencesView.loadPreference("language", "Italian");
        assertEquals("Italian", result, "Dovrebbe restituire il valore di default 'Italian' quando il file non esiste.");
    }

    @Test
    void testLoadPreferenceWhenFileIsCorrupted() throws IOException {
        // Crea un file di preferenze corrotto
        Files.writeString(PREFS_FILE_PATH, "corrupted content");

        // Verifica che venga restituito il valore di default e che il file corrotto non causi crash
        String result = PreferencesView.loadPreference("language", "Italian");
        assertEquals("Italian", result, "Dovrebbe restituire il valore di default 'Italian' quando il file è corrotto.");
    }
}
