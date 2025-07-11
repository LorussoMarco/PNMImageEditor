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
}
