package ch.supsi.os.backend.business;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Rotate90ClockwiseTransformationTest {
    private ImageModel imageModel;

    @BeforeEach
    void setUp() {
        int[][] pixels = {
                {0, 1, 2},
                {3, 4, 5},
                {6, 7, 8}
        };
        imageModel = new ImageModel("P2", 3, 3, pixels, 1);
    }

    @Test
    void testRotate90Clockwise() {
        Rotate90ClockwiseTransformation transformation = new Rotate90ClockwiseTransformation();
        transformation.applyTransformation(imageModel);

        int[][] expectedPixels = {
                {6, 3, 0},
                {7, 4, 1},
                {8, 5, 2}
        };
        assertArrayEquals(expectedPixels, imageModel.getPixels());
    }
}
