package ch.supsi.os.backend.model;

import ch.supsi.os.backend.model.ImageModel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ImageModelTest {

    @Test
    public void testConstructorAndGetters() {
        int[][] pixels = {
                {255, 128, 0},
                {64, 32, 16}
        };
        ImageModel model = new ImageModel("P2", 3, 2, pixels, 1);

        assertEquals("P2", model.getMagicNumber());
        assertEquals(3, model.getWidth());
        assertEquals(2, model.getHeight());
        assertEquals(1, model.getChannels());
        assertArrayEquals(pixels, model.getPixels());
    }

    @Test
    public void testSettersAndGetters() {
        ImageModel model = new ImageModel();

        model.setMagicNumber("P3");
        model.setWidth(4);
        model.setHeight(3);
        model.setChannels(3);

        int[][] pixels = {
                {255, 0, 0, 0},
                {0, 255, 0, 255}
        };
        model.setPixels(pixels);

        assertEquals("P3", model.getMagicNumber());
        assertEquals(4, model.getWidth());
        assertEquals(3, model.getHeight());
        assertEquals(3, model.getChannels());
        assertArrayEquals(pixels, model.getPixels());
    }

    @Test
    public void testToString() {
        int[][] pixels = {
                {255, 128, 0},
                {64, 32, 16}
        };
        ImageModel model = new ImageModel("P2", 3, 2, pixels, 1);

        String expectedOutput = "PBMModel{magicNumber='P2', width=3, height=2, pixels=\n" +
                " 255 128 0\n" +
                " 64 32 16\n";

        assertEquals(expectedOutput, model.toString());
    }

    @Test
    public void testPixelToString() {
        int[][] pixels = {
                {0, 128, 255},
                {255, 64, 32}
        };
        ImageModel model = new ImageModel("P3", 3, 2, pixels, 3);

        String expectedPixels = " 0 128 255\n 255 64 32\n";
        assertEquals(expectedPixels, model.toString().split("pixels=\n")[1]); // We just want to test pixel formatting here
    }

    @Test
    public void testFlipImageUpsideDown() {
        int[][] originalPixels = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        ImageModel imageModel = new ImageModel("P2", 3, 3, originalPixels, 1);
        imageModel.flipImageUpsideDown();
        int[][] expectedPixels = {
                {7, 8, 9},
                {4, 5, 6},
                {1, 2, 3}
        };
        assertArrayEquals(expectedPixels, imageModel.getPixels(), "The image should be flipped upside down");
    }

    @Test
    public void testFlipImageSideToSideForRGB() {
        int[][] originalPixels = {
                {0, 4, 170, 0, 255, 0, 0, 0, 255},
                {255, 255, 0, 30, 24, 96, 0, 0, 0}
        };

        ImageModel imageModel = new ImageModel("P3", 3, 2, originalPixels, 3);

        imageModel.flipImageSideToSide();

        int[][] expectedPixels = {
                {0, 0, 255, 0, 255, 0, 0, 4, 170},
                {0, 0, 0, 30, 24, 96, 255, 255, 0}
        };

        assertArrayEquals(expectedPixels, imageModel.getPixels(), "The RGB image should be flipped side-to-side");
    }

    @Test
    public void testRotate90DegreesClockwise() {
        int[][] originalPixels = {
                {1, 2, 3},
                {4, 5, 6}
        };

        ImageModel imageModel = new ImageModel("P2", 3, 2, originalPixels, 1);
        imageModel.rotate90Clockwise();

        int[][] expectedPixels = {
                {4, 1},
                {5, 2},
                {6, 3}
        };

        assertEquals(2, imageModel.getWidth(), "The new width should match the original height");
        assertEquals(3, imageModel.getHeight(), "The new height should match the original width");
        assertArrayEquals(expectedPixels, imageModel.getPixels(), "The image should be rotated 90 degrees clockwise");
    }

    @Test
    public void testRotate90DegreesClockwiseForRGB() {
        int[][] originalPixels = {
                {0, 4, 170, 0, 255, 0}, // Pixel (0, 0): [0, 4, 170], Pixel (0, 1): [0, 255, 0]
                {255, 255, 0, 30, 24, 96}  // Pixel (1, 0): [255, 255, 0], Pixel (1, 1): [30, 24, 96]
        };

        ImageModel imageModel = new ImageModel("P3", 2, 2, originalPixels, 3);
        imageModel.rotate90Clockwise();

        int[][] expectedPixels = {
                {255, 255, 0, 0, 4, 170},  // Pixel (0, 0): [255, 255, 0], Pixel (0, 1): [0, 4, 170]
                {30, 24, 96, 0, 255, 0}  // Pixel (1, 0): [30, 24, 96], Pixel (1, 1): [0, 255, 0]
        };

        assertEquals(2, imageModel.getWidth(), "The new width should match the original height");
        assertEquals(2, imageModel.getHeight(), "The new height should match the original width");
        assertArrayEquals(expectedPixels, imageModel.getPixels(), "The RGB image should be rotated 90 degrees clockwise");
    }

}
