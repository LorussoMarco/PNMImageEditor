package ch.supsi.os.backend.dataAccess;

import ch.supsi.os.backend.business.ImageModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class PgmHandlerTest {
    private PgmHandler pgmHandler;
    private ImageModel imageModel;

    @BeforeEach
    void setUp() {
        pgmHandler = new PgmHandler();
        imageModel = new ImageModel();
    }

    @Test
    void testCanHandleValidPgmFile() throws IOException {
        File tempFile = createTempPgmFile("P2\n3 3\n255\n100 150 200\n0 50 100\n200 250 0");
        assertTrue(pgmHandler.canHandle(tempFile.getPath(), imageModel));
        tempFile.delete();
    }

    @Test
    void testCannotHandleNonPgmFile() throws IOException {
        File tempFile = createTempPgmFile("P1\n3 3\n100 150 200\n0 50 100\n200 250 0");
        assertFalse(pgmHandler.canHandle(tempFile.getPath(), imageModel));
        tempFile.delete();
    }

    @Test
    void testProcessValidPgmFile() throws IOException {
        File tempFile = createTempPgmFile("P2\n# This is a comment\n3 3\n255\n100 150 200\n0 50 100\n200 250 0");
        pgmHandler.process(tempFile.getPath(), imageModel);

        assertEquals("P2", imageModel.getMagicNumber());
        assertEquals(3, imageModel.getWidth());
        assertEquals(3, imageModel.getHeight());
        assertEquals(1, imageModel.getChannels());

        int[][] expectedPixels = {
                {100, 150, 200},
                {0, 50, 100},
                {200, 250, 0}
        };
        assertArrayEquals(expectedPixels, imageModel.getPixels());

        tempFile.delete();
    }

    @Test
    void testProcessPgmFileWithCommentsAndNormalization() throws IOException {
        File tempFile = createTempPgmFile("P2\n# Comment line\n3 3\n100\n50 75 100\n25 50 75\n100 25 50");
        pgmHandler.process(tempFile.getPath(), imageModel);

        assertEquals("P2", imageModel.getMagicNumber());
        assertEquals(3, imageModel.getWidth());
        assertEquals(3, imageModel.getHeight());
        assertEquals(1, imageModel.getChannels());

        int[][] expectedPixels = {
                {127, 191, 255},  // Pixel values normalized to 0-255 range
                {63, 127, 191},
                {255, 63, 127}
        };
        assertArrayEquals(expectedPixels, imageModel.getPixels());

        tempFile.delete();
    }

    private File createTempPgmFile(String content) throws IOException {
        File tempFile = File.createTempFile("test", ".pgm");
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(content);
        }
        return tempFile;
    }

    @Test
    void testSave() throws IOException {
        // Set up an ImageModel with known values
        imageModel.setMagicNumber("P2");
        imageModel.setWidth(3);
        imageModel.setHeight(3);
        imageModel.setChannels(1);
        imageModel.setPixels(new int[][] {
                {100, 150, 200},
                {0, 50, 100},
                {200, 250, 0}
        });

        // Create a temporary file to save the image
        File tempFile = File.createTempFile("testSave", ".pgm");
        pgmHandler.save(tempFile.getPath(), imageModel);

        // Read the saved file and verify its contents
        try (BufferedReader reader = new BufferedReader(new FileReader(tempFile))) {
            assertEquals("P2", reader.readLine().trim());  // Magic number
            assertEquals("# Created by PgmSaveHandler", reader.readLine().trim());  // Comment
            assertEquals("3 3", reader.readLine().trim());  // Width and height
            assertEquals("255", reader.readLine().trim());  // Max gray value

            // Check pixel values line by line
            assertEquals("100 150 200", reader.readLine().trim());
            assertEquals("0 50 100", reader.readLine().trim());
            assertEquals("200 250 0", reader.readLine().trim());
        }

        // Clean up
        tempFile.delete();
    }

}
