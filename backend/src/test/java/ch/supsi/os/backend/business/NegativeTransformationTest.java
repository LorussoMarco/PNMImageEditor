package ch.supsi.os.backend.business;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NegativeTransformationTest {
    private ImageModel imageModel;

    @BeforeEach
    void setUp() {
        int[][] pixels = {
                {0, 100, 200},
                {50, 150, 255},
                {25, 75, 125}
        };
        imageModel = new ImageModel("P2", 3, 3, pixels, 1);
    }

    @Test
    void testNegativeTransformation() {
        NegativeTransformation transformation = new NegativeTransformation();
        transformation.applyTransformation(imageModel);

        int[][] expectedPixels = {
                {255, 155, 55},
                {205, 105, 0},
                {230, 180, 130}
        };
        assertArrayEquals(expectedPixels, imageModel.getPixels());
    }
}
