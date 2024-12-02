package ch.supsi.os.backend.business;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PbmFormatConverterTest {

    @Test
    public void testSupportsMethod() {
        PbmFormatConverter converter = new PbmFormatConverter();
        assertTrue(converter.supports("P1"));
        assertFalse(converter.supports("P2"));
        assertFalse(converter.supports("P3"));
    }

    @Test
    public void testConvertFromPGM() {
        int[][] pgmPixels = {
                {255, 128, 0},
                {64, 200, 255}
        };

        ImageModel pgmImage = new ImageModel("P2", 3, 2, pgmPixels, 1);
        PbmFormatConverter converter = new PbmFormatConverter(128);

        ImageModel pbmImage = converter.convert(pgmImage);

        assertEquals("P1", pbmImage.getMagicNumber());
        assertEquals(3, pbmImage.getWidth());
        assertEquals(2, pbmImage.getHeight());
        assertEquals(1, pbmImage.getChannels());

        int[][] expectedPixels = {
                {1, 1, 0},
                {0, 1, 1}
        };
        assertArrayEquals(expectedPixels, pbmImage.getPixels());
    }

    @Test
    public void testConvertFromPPM() {
        int[][] originalPixels = {
                {255, 0, 0, 0, 255, 0, 0, 0, 255},  // Prima riga
                {255, 255, 0, 0, 255, 255, 255, 0, 255} // Seconda riga
        };

        ImageModel sourceImage = new ImageModel("P3", 3, 2, originalPixels, 3);

        PbmFormatConverter converter = new PbmFormatConverter();
        ImageModel convertedImage = converter.convert(sourceImage);

        int[][] expectedPixels = {
                {0, 1, 0},
                {1, 1, 0}
        };

        assertNotNull(convertedImage);
        assertEquals("P1", convertedImage.getMagicNumber());
        assertEquals(3, convertedImage.getWidth());
        assertEquals(2, convertedImage.getHeight());
        assertEquals(1, convertedImage.getChannels());
        assertArrayEquals(expectedPixels, convertedImage.getPixels());
    }




    @Test
    public void testConvertFromPBM() {
        int[][] pbmPixels = {
                {1, 0, 1},
                {0, 1, 0}
        };

        ImageModel pbmImage = new ImageModel("P1", 3, 2, pbmPixels, 1);
        PbmFormatConverter converter = new PbmFormatConverter();

        ImageModel convertedImage = converter.convert(pbmImage);

        assertSame(pbmImage, convertedImage);
    }

    @Test
    public void testConvertUnsupportedFormat() {
        int[][] pixels = {
                {255, 128, 0},
                {64, 200, 255}
        };

        ImageModel unsupportedImage = new ImageModel("P4", 3, 2, pixels, 1);
        PbmFormatConverter converter = new PbmFormatConverter();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                converter.convert(unsupportedImage)
        );

        assertEquals("Unsupported source image format for conversion to PBM.", exception.getMessage());
    }
}
