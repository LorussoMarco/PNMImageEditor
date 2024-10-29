package ch.supsi.os.backend.business;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlipSideToSideTransformationTest {
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
    void testFlipSideToSide() {
        FlipSideToSideTransformation transformation = new FlipSideToSideTransformation();
        transformation.applyTransformation(imageModel);

        int[][] expectedPixels = {
                {2, 1, 0},
                {5, 4, 3},
                {8, 7, 6}
        };
        assertArrayEquals(expectedPixels, imageModel.getPixels());
    }
}
