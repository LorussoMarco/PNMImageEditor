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
        tempFile = File.createTempFile("testImage", ".pgm");
        tempFile.deleteOnExit();
    }

    @AfterEach
    public void tearDown() {
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Test
    public void testLoadPGMImage() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("P2\n"); // Magic number for grayscale
            writer.write("# Example comment\n");
            writer.write("3 2\n"); // Width = 3, Height = 2
            writer.write("255\n"); // Max gray value
            writer.write("255 128 0\n");
            writer.write("64 32 16\n");
        }

        ImageController controller = ImageController.getInstance();
        controller.loadImageFromFile(tempFile.getAbsolutePath());

        // Get the image model and verify its properties
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
    public void testLoadPBMImage() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("P1\n"); // Magic number for binary image
            writer.write("3 2\n"); // Width = 3, Height = 2
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
    public void testLoadPPMImage() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("P3\n"); // Magic number for RGB image
            writer.write("3 2\n"); // Width = 3, Height = 2
            writer.write("255\n"); // Max color value
            writer.write("255 0 0 0 255 0 0 0 255\n");
            writer.write("255 255 0 0 255 255 255 0 255\n");
        }

        ImageController controller = ImageController.getInstance();
        controller.loadImageFromFile(tempFile.getAbsolutePath());

        ImageModel imageModel = controller.getImageModel();
        assertNotNull(imageModel);
        assertEquals("P3", imageModel.getMagicNumber());
        assertEquals(3, imageModel.getWidth()); // 3 pixels per row
        assertEquals(2, imageModel.getHeight()); // 2 rows
        assertEquals(3, imageModel.getChannels()); // 3 channels for RGB

        int[][] expectedPixels = {
                {255, 0, 0, 0, 255, 0, 0, 0, 255},  // First row: Red, Green, Blue
                {255, 255, 0, 0, 255, 255, 255, 0, 255}  // Second row: Yellow, Cyan, Magenta
        };
        assertArrayEquals(expectedPixels, imageModel.getPixels());
    }


    @Test
    public void testLoadUnsupportedFormat() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("P5\n"); // Unsupported magic number
            writer.write("3 2\n");
            writer.write("255\n");
        }

        ImageController controller = ImageController.getInstance();

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                controller.loadImageFromFile(tempFile.getAbsolutePath())
        );

        assertEquals("Unsupported image format", exception.getMessage());
    }

    @Test
    public void testSaveImageToFile() throws IOException {
        // Set up the ImageModel with known values for saving
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

        // Save the image model to a file
        controller.saveImageToFile(tempFile.getAbsolutePath());

        // Verify the contents of the saved file
        try (BufferedReader reader = new BufferedReader(new FileReader(tempFile))) {
            assertEquals("P2", reader.readLine().trim());  // Magic number
            assertTrue(reader.readLine().startsWith("#")); // Comment line
            assertEquals("3 2", reader.readLine().trim()); // Width and height
            assertEquals("255", reader.readLine().trim()); // Max gray value
            assertEquals("255 128 0", reader.readLine().trim());
            assertEquals("64 32 16", reader.readLine().trim());
        }
    }
}
