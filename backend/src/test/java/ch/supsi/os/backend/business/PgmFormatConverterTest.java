package ch.supsi.os.backend.business;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PgmFormatConverterTest {

    @Test
    public void testSupports() {
        PgmFormatConverter converter = new PgmFormatConverter();

        assertTrue(converter.supports("P2"));
        assertFalse(converter.supports("P1"));
        assertFalse(converter.supports("P3"));
    }

    @Test
    public void testConvertFromPBM() {
        int[][] pbmPixels = {
                {1, 0, 1},
                {0, 1, 0}
        };

        ImageModel pbmImage = new ImageModel("P1", 3, 2, pbmPixels, 1);

        PgmFormatConverter converter = new PgmFormatConverter();
        ImageModel convertedImage = converter.convert(pbmImage);

        int[][] expectedPixels = {
                {255, 0, 255},
                {0, 255, 0}
        };

        assertNotNull(convertedImage);
        assertEquals("P2", convertedImage.getMagicNumber());
        assertEquals(3, convertedImage.getWidth());
        assertEquals(2, convertedImage.getHeight());
        assertEquals(1, convertedImage.getChannels());
        assertArrayEquals(expectedPixels, convertedImage.getPixels());
    }

    @Test
    public void testConvertFromPPM() {
        int[][] ppmPixels = {
                {255, 0, 0, 0, 255, 0, 0, 0, 255},
                {255, 255, 0, 0, 255, 255, 255, 0, 255}
        };

        ImageModel ppmImage = new ImageModel("P3", 3, 2, ppmPixels, 3);

        PgmFormatConverter converter = new PgmFormatConverter();
        ImageModel convertedImage = converter.convert(ppmImage);

        int[][] expectedPixels = {
                {85, 85, 85},
                {170, 170, 170}
        };

        assertNotNull(convertedImage);
        assertEquals("P2", convertedImage.getMagicNumber());
        assertEquals(3, convertedImage.getWidth());
        assertEquals(2, convertedImage.getHeight());
        assertEquals(1, convertedImage.getChannels());
        assertArrayEquals(expectedPixels, convertedImage.getPixels());
    }

    @Test
    public void testUnsupportedFormat() {
        int[][] unsupportedPixels = {
                {255, 0, 0},
                {0, 255, 0}
        };

        ImageModel unsupportedImage = new ImageModel("P5", 3, 2, unsupportedPixels, 1);

        PgmFormatConverter converter = new PgmFormatConverter();

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                converter.convert(unsupportedImage)
        );

        assertEquals("Unsupported source image format for conversion to PGM.", exception.getMessage());
    }

    @Test
    public void testConvertFromPGM() {
        int[][] pgmPixels = {
                {128, 64, 32},
                {255, 0, 16}
        };

        ImageModel pgmImage = new ImageModel("P2", 3, 2, pgmPixels, 1);

        PgmFormatConverter converter = new PgmFormatConverter();
        ImageModel convertedImage = converter.convert(pgmImage);

        assertNotNull(convertedImage);
        assertSame(pgmImage, convertedImage);
    }
}
