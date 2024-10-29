package ch.supsi.os.backend.dataAccess;

import ch.supsi.os.backend.business.ImageModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PpmHandlerTest {

    private PpmHandler ppmHandler;
    private ImageModel imageModel;
    private File tempFile;

    @BeforeEach
    public void setUp() throws IOException {
        ppmHandler = new PpmHandler();
        imageModel = new ImageModel();

        // Creiamo un file PPM di test temporaneo
        tempFile = File.createTempFile("test", ".ppm");
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("P3\n");
            writer.write("# Test PPM file\n");
            writer.write("3 2\n");
            writer.write("255\n");
            writer.write("255 0 0  0 255 0  0 0 255\n");
            writer.write("128 128 128  64 64 64  32 32 32\n");
        }
    }

    @AfterEach
    public void tearDown() {
        tempFile.delete(); // Rimuovi il file temporaneo dopo ogni test
    }

    @Test
    public void testCanHandle() throws IOException {
        assertTrue(ppmHandler.canHandle(tempFile.getAbsolutePath(), imageModel),
                "PpmHandler should handle files with 'P3' magic number");
    }

    @Test
    public void testProcess() throws IOException {
        ppmHandler.process(tempFile.getAbsolutePath(), imageModel);

        assertEquals("P3", imageModel.getMagicNumber(), "Magic number should be 'P3'");
        assertEquals(3, imageModel.getWidth(), "Width should be 3");
        assertEquals(2, imageModel.getHeight(), "Height should be 2");
        assertEquals(3, imageModel.getChannels(), "Channels should be 3 for PPM files");

        int[][] expectedPixels = {
                {255, 0, 0, 0, 255, 0, 0, 0, 255},
                {128, 128, 128, 64, 64, 64, 32, 32, 32}
        };
        assertArrayEquals(expectedPixels, imageModel.getPixels(), "Pixels do not match expected values");
    }
}
