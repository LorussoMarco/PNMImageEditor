package ch.supsi.os.backend.business;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Rotate90AntiClockwiseTransformationTest {
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
    void testRotate90AntiClockwise() {
        Rotate90AntiClockwiseTransformation transformation = new Rotate90AntiClockwiseTransformation();
        transformation.applyTransformation(imageModel);

        int[][] expectedPixels = {
                {2, 5, 8},
                {1, 4, 7},
                {0, 3, 6}
        };
        assertArrayEquals(expectedPixels, imageModel.getPixels());
    }
}
