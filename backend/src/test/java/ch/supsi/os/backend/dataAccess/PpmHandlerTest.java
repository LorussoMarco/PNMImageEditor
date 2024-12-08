package ch.supsi.os.backend.dataAccess;

import ch.supsi.os.backend.business.ImageModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PpmHandlerTest {

    private PpmHandler ppmHandler;
    private ImageModel imageModel;
    private File tempFile;

    @BeforeEach
    public void setUp() throws IOException {
        ppmHandler = new PpmHandler();
        imageModel = new ImageModel();

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
        tempFile.delete();
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

    @Test
    public void testSave() throws IOException {
        imageModel.setMagicNumber("P3");
        imageModel.setWidth(3);
        imageModel.setHeight(2);
        imageModel.setChannels(3);
        int[][] pixels = {
                {255, 0, 0, 0, 255, 0, 0, 0, 255},
                {128, 128, 128, 64, 64, 64, 32, 32, 32}
        };
        imageModel.setPixels(pixels);

        File saveFile = File.createTempFile("test_save", ".ppm");
        saveFile.deleteOnExit();

        ppmHandler.save(saveFile.getAbsolutePath(), imageModel);

        List<String> lines = Files.readAllLines(saveFile.toPath());

        assertEquals("P3", lines.get(0).trim(), "Magic number mismatch");
        assertEquals("3 2", lines.get(1).trim(), "Dimensions mismatch");
        assertEquals("255", lines.get(2).trim(), "Max color value mismatch");

        String[] expectedPixelLines = {
                "255 0 0 0 255 0 0 0 255",
                "128 128 128 64 64 64 32 32 32"
        };

        for (int i = 0; i < expectedPixelLines.length; i++) {
            assertEquals(expectedPixelLines[i], lines.get(i + 3).trim(), "Pixel row " + (i + 1) + " mismatch");
        }
    }

    private File createTempPbmFile(String content) throws IOException {
        File tempFile = File.createTempFile("test", ".pbm");
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(content);
        }
        return tempFile;
    }
}
