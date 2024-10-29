package ch.supsi.os.backend.business;

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

    @Test
    public void testRotate90AntiClockwise() {
        int[][] originalPixels = {
                {1, 2, 3},
                {4, 5, 6}
        };

        ImageModel imageModel = new ImageModel("P2", 3, 2, originalPixels, 1);
        imageModel.rotate90AntiClockwise();

        int[][] expectedPixels = {
                {3, 6},
                {2, 5},
                {1, 4}
        };

        assertEquals(2, imageModel.getWidth(), "The new width should match the original height");
        assertEquals(3, imageModel.getHeight(), "The new height should match the original width");
        assertArrayEquals(expectedPixels, imageModel.getPixels(), "The image should be rotated 90 degrees anti-clockwise");
    }

    @Test
    public void testRotate90AntiClockwiseForRGB() {
        int[][] originalPixels = {
                {0, 4, 170, 0, 255, 0},
                {255, 255, 0, 30, 24, 96}
        };

        ImageModel imageModel = new ImageModel("P3", 2, 2, originalPixels, 3);
        imageModel.rotate90AntiClockwise();

        int[][] expectedPixels = {
                {0, 255, 0, 30, 24, 96},  // Rotated row 0
                {0, 4, 170, 255, 255, 0}    // Rotated row 1
        };

        assertEquals(2, imageModel.getWidth(), "The new width should match the original height");
        assertEquals(2, imageModel.getHeight(), "The new height should match the original width");
        assertArrayEquals(expectedPixels, imageModel.getPixels(), "The RGB image should be rotated 90 degrees anti-clockwise");
    }

    @Test
    public void testNegativeTransformationGrayscale() {
        int[][] originalPixels = {
                {0},   // Black
                {128}, // Mid gray
                {255}  // White
        };

        ImageModel imageModel = new ImageModel("P2", 1, 3, originalPixels, 1);
        imageModel.negativeTransformation();

        int[][] expectedPixels = {
                {255}, // Negative of black
                {127}, // Negative of mid gray
                {0}    // Negative of white
        };

        assertArrayEquals(expectedPixels, imageModel.getPixels(), "The grayscale image should be transformed to negative correctly");
    }

    @Test
    public void testNegativeTransformationRGB() {
        int[][] originalPixels = {
                {255, 0, 0},   // Red
                {0, 255, 0},   // Green
                {0, 0, 255},   // Blue
                {255, 255, 255}, // White
                {0, 0, 0}      // Black
        };

        ImageModel imageModel = new ImageModel("P3", 3, 5, originalPixels, 3);
        imageModel.negativeTransformation();

        int[][] expectedPixels = {
                {0, 255, 255},   // Negative of red
                {255, 0, 255},   // Negative of green
                {255, 255, 0},   // Negative of blue
                {0, 0, 0},       // Negative of white
                {255, 255, 255}  // Negative of black
        };

        assertArrayEquals(expectedPixels, imageModel.getPixels(), "The RGB image should be transformed to negative correctly");
    }



}
