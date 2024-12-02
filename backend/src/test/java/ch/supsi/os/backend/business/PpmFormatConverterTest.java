package ch.supsi.os.backend.business;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PpmFormatConverterTest {

    @Test
    public void testSupports() {
        PpmFormatConverter converter = new PpmFormatConverter();

        assertTrue(converter.supports("P3"));
        assertFalse(converter.supports("P1"));
        assertFalse(converter.supports("P2"));
    }

    @Test
    public void testConvertFromPBM() {
        // Simulazione di immagine PBM
        int[][] pbmPixels = {
                {1, 0, 1},
                {0, 1, 0}
        };

        ImageModel pbmImage = new ImageModel("P1", 3, 2, pbmPixels, 1);

        PpmFormatConverter converter = new PpmFormatConverter();
        ImageModel convertedImage = converter.convert(pbmImage);

        int[][] expectedPixels = {
                {255, 255, 255, 0, 0, 0, 255, 255, 255},
                {0, 0, 0, 255, 255, 255, 0, 0, 0}
        };

        assertNotNull(convertedImage);
        assertEquals("P3", convertedImage.getMagicNumber());
        assertEquals(3, convertedImage.getWidth());
        assertEquals(2, convertedImage.getHeight());
        assertEquals(3, convertedImage.getChannels());
        assertArrayEquals(expectedPixels, convertedImage.getPixels());
    }

    @Test
    public void testConvertFromPGM() {
        int[][] pgmPixels = {
                {128, 64, 32},
                {255, 0, 16}
        };

        ImageModel pgmImage = new ImageModel("P2", 3, 2, pgmPixels, 1);

        PpmFormatConverter converter = new PpmFormatConverter();
        ImageModel convertedImage = converter.convert(pgmImage);

        int[][] expectedPixels = {
                {128, 128, 128, 64, 64, 64, 32, 32, 32},
                {255, 255, 255, 0, 0, 0, 16, 16, 16}
        };

        assertNotNull(convertedImage);
        assertEquals("P3", convertedImage.getMagicNumber());
        assertEquals(3, convertedImage.getWidth());
        assertEquals(2, convertedImage.getHeight());
        assertEquals(3, convertedImage.getChannels());
        assertArrayEquals(expectedPixels, convertedImage.getPixels());
    }

    @Test
    public void testConvertFromPPM() {
        int[][] ppmPixels = {
                {255, 0, 0, 0, 255, 0, 0, 0, 255},
                {255, 255, 0, 0, 255, 255, 255, 0, 255}
        };

        ImageModel ppmImage = new ImageModel("P3", 3, 2, ppmPixels, 3);

        PpmFormatConverter converter = new PpmFormatConverter();
        ImageModel convertedImage = converter.convert(ppmImage);

        assertNotNull(convertedImage);
        assertSame(ppmImage, convertedImage);
    }

    @Test
    public void testUnsupportedFormat() {
        int[][] unsupportedPixels = {
                {1, 2, 3},
                {4, 5, 6}
        };
        ImageModel unsupportedImage = new ImageModel("P5", 3, 2, unsupportedPixels, 1); // "P5" non è supportato

        PpmFormatConverter converter = new PpmFormatConverter();

        assertThrows(IllegalArgumentException.class, () -> converter.convert(unsupportedImage));
    }

}
