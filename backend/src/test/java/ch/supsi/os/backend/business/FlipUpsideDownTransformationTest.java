package ch.supsi.os.backend.business;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlipUpsideDownTransformationTest {
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
    void testFlipUpsideDown() {
        FlipUpsideDownTransformation transformation = new FlipUpsideDownTransformation();
        transformation.applyTransformation(imageModel);

        int[][] expectedPixels = {
                {6, 7, 8},
                {3, 4, 5},
                {0, 1, 2}
        };
        assertArrayEquals(expectedPixels, imageModel.getPixels());
    }
}
