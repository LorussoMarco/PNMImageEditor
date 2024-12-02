package ch.supsi.os.backend.application;

import ch.supsi.os.backend.business.ImageModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class ImageControllerTest {

    private File tempFile;

    @BeforeEach
    public void setUp() throws IOException {
        tempFile = File.createTempFile("testImage", ".pnm");
        tempFile.deleteOnExit();
    }

    @AfterEach
    public void tearDown() {
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Test
    public void testLoadPBMImage() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("P1\n");
            writer.write("3 2\n");
            writer.write("1 0 1\n");
            writer.write("0 1 0\n");
        }

        ImageController controller = ImageController.getInstance();
        controller.loadImageFromFile(tempFile.getAbsolutePath());

        ImageModel imageModel = controller.getImageModel();
        assertNotNull(imageModel);
        assertEquals("P1", imageModel.getMagicNumber());
        assertEquals(3, imageModel.getWidth());
        assertEquals(2, imageModel.getHeight());
        assertEquals(1, imageModel.getChannels());

        int[][] expectedPixels = {
                {255, 0, 255},
                {0, 255, 0}
        };
        assertArrayEquals(expectedPixels, imageModel.getPixels());
    }

    @Test
    public void testLoadPGMImage() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("P2\n");
            writer.write("3 2\n");
            writer.write("255\n");
            writer.write("255 128 0\n");
            writer.write("64 32 16\n");
        }

        ImageController controller = ImageController.getInstance();
        controller.loadImageFromFile(tempFile.getAbsolutePath());

        ImageModel imageModel = controller.getImageModel();
        assertNotNull(imageModel);
        assertEquals("P2", imageModel.getMagicNumber());
        assertEquals(3, imageModel.getWidth());
        assertEquals(2, imageModel.getHeight());
        assertEquals(1, imageModel.getChannels());

        int[][] expectedPixels = {
                {255, 128, 0},
                {64, 32, 16}
        };
        assertArrayEquals(expectedPixels, imageModel.getPixels());
    }

    @Test
    public void testLoadPPMImage() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("P3\n");
            writer.write("3 2\n");
            writer.write("255\n");
            writer.write("255 0 0 0 255 0 0 0 255\n");
            writer.write("255 255 0 0 255 255 255 0 255\n");
        }

        ImageController controller = ImageController.getInstance();
        controller.loadImageFromFile(tempFile.getAbsolutePath());

        ImageModel imageModel = controller.getImageModel();
        assertNotNull(imageModel);
        assertEquals("P3", imageModel.getMagicNumber());
        assertEquals(3, imageModel.getWidth());
        assertEquals(2, imageModel.getHeight());
        assertEquals(3, imageModel.getChannels());

        int[][] expectedPixels = {
                {255, 0, 0, 0, 255, 0, 0, 0, 255},
                {255, 255, 0, 0, 255, 255, 255, 0, 255}
        };
        assertArrayEquals(expectedPixels, imageModel.getPixels());
    }

    @Test
    public void testLoadUnsupportedFormat() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("P5\n");
            writer.write("3 2\n");
            writer.write("255\n");
        }

        ImageController controller = ImageController.getInstance();

        IOException exception = assertThrows(IOException.class, () ->
                controller.loadImageFromFile(tempFile.getAbsolutePath())
        );

        assertTrue(exception.getMessage().contains("Unsupported or invalid image format"));
    }

    @Test
    public void testSaveImageToFile() throws IOException {
        ImageController controller = ImageController.getInstance();
        ImageModel model = controller.getImageModel();
        model.setMagicNumber("P2");
        model.setWidth(3);
        model.setHeight(2);
        model.setChannels(1);
        model.setPixels(new int[][]{
                {255, 128, 0},
                {64, 32, 16}
        });

        controller.saveImageToFile(tempFile.getAbsolutePath());

        try (BufferedReader reader = new BufferedReader(new FileReader(tempFile))) {
            assertEquals("P2", reader.readLine().trim());
            assertTrue(reader.readLine().startsWith("#"));
            assertEquals("3 2", reader.readLine().trim());
            assertEquals("255", reader.readLine().trim());
            assertEquals("255 128 0", reader.readLine().trim());
            assertEquals("64 32 16", reader.readLine().trim());
        }
    }

    @Test
    public void testSaveImageAs() throws IOException {
        ImageController controller = ImageController.getInstance();
        ImageModel model = controller.getImageModel();
        model.setMagicNumber("P2");
        model.setWidth(3);
        model.setHeight(2);
        model.setChannels(1);
        model.setPixels(new int[][]{
                {255, 128, 0},
                {64, 32, 16}
        });

        controller.saveImageAs(tempFile.getAbsolutePath(), "P1");

        try (BufferedReader reader = new BufferedReader(new FileReader(tempFile))) {
            assertEquals("P1", reader.readLine().trim());
            assertEquals("3 2", reader.readLine().trim());
            assertEquals("1 1 0", reader.readLine().trim());
            assertEquals("0 0 0", reader.readLine().trim());
        }
    }
}
