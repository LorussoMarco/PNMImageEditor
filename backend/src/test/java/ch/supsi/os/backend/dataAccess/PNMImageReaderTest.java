package ch.supsi.os.backend.dataAccess;

import static org.junit.jupiter.api.Assertions.*;

import ch.supsi.os.backend.application.ImageController;
import ch.supsi.os.backend.business.ImageModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class PNMImageReaderTest {

    private File tempFile;
    private PNMImageReader pnmReader;
    private ImageController imageController;

    @BeforeEach
    public void setUp() throws IOException {
        String testP3FileContent =
                "P3\n" +
                        "# Example PNM file\n" +
                        "4 4\n" +
                        "255\n" +
                        "255 0 0  0 255 0  0 0 255  255 255 0\n" +
                        "255 255 255  128 128 128  64 64 64  32 32 32\n" +
                        "255 0 255  0 255 255  255 255 255  0 0 0\n" +
                        "255 255 255  0 0 0  128 128 128  64 64 64\n";

        tempFile = File.createTempFile("test_p3", ".pnm");
        Files.write(tempFile.toPath(), testP3FileContent.getBytes());
        tempFile.deleteOnExit();

        pnmReader = new PNMImageReader(tempFile.getAbsolutePath());
        imageController = ImageController.getInstance();
    }

    @AfterEach
    public void tearDown() throws IOException {
        if (pnmReader != null) {
            pnmReader.close();
        }
    }

    @Test
    public void testLoadImageFromFile() throws IOException {
        imageController.loadImageFromFile(tempFile.getAbsolutePath());

        ImageModel imageModel = imageController.getImageModel();

        assertEquals("P3", imageModel.getMagicNumber(), "Magic number should be P3");

        assertEquals(4, imageModel.getWidth(), "Width should be 4");
        assertEquals(4, imageModel.getHeight(), "Height should be 4");

        assertEquals(3, imageModel.getChannels(), "There should be 3 channels for P3");

        int[][] pixels = imageModel.getPixels();
        assertArrayEquals(new int[] {255, 0, 0, 0, 255, 0, 0, 0, 255, 255, 255, 0}, pixels[0], "First row of pixels does not match expected values");

        assertArrayEquals(new int[] {255, 255, 255, 128, 128, 128, 64, 64, 64, 32, 32, 32}, pixels[1], "Second row of pixels does not match expected values");
    }

    @Test
    public void testInvalidFilePath() {
        assertThrows(IOException.class, () -> {
            new PNMImageReader("invalid_path.pnm");
        }, "Should throw IOException for invalid file path");
    }

    @Test
    public void testUnsupportedFormat() throws IOException {
        String testUnsupportedFileContent =
                "P4\n" +
                        "# Unsupported PNM file\n" +
                        "2 2\n" +
                        "11001100\n";

        tempFile = File.createTempFile("test_p4", ".pnm");
        Files.write(tempFile.toPath(), testUnsupportedFileContent.getBytes());
        tempFile.deleteOnExit();

        assertThrows(IllegalArgumentException.class, () -> {
            imageController.loadImageFromFile(tempFile.getAbsolutePath());
        }, "Should throw IllegalArgumentException for unsupported format P4");
    }
}
