package ch.supsi.os.backend.business;

public class PgmFormatConverter implements FormatConverter {

    @Override
    public boolean supports(String magicNumber) {
        return "P2".equals(magicNumber);
    }

    @Override
    public ImageModel convert(ImageModel sourceImage) {
        if (sourceImage.getMagicNumber().equals("P2")) {
            return sourceImage; // Already in PGM format
        }

        int width = sourceImage.getWidth();
        int height = sourceImage.getHeight();
        int[][] originalPixels = sourceImage.getPixels();
        int[][] convertedPixels = new int[height][width];

        if (sourceImage.getChannels() == 3) { // RGB to Grayscale
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int index = x * 3;
                    int r = originalPixels[y][index];
                    int g = originalPixels[y][index + 1];
                    int b = originalPixels[y][index + 2];
                    // Average the RGB values for grayscale
                    convertedPixels[y][x] = (r + g + b) / 3;
                }
            }
        } else if (sourceImage.getChannels() == 1 && sourceImage.getMagicNumber().equals("P1")) { // PBM (binary) to PGM
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // Scale binary (0 or 1) to grayscale (0-255)
                    convertedPixels[y][x] = originalPixels[y][x] * 255;
                }
            }
        } else {
            throw new IllegalArgumentException("Unsupported source image format for conversion to PGM.");
        }

        return new ImageModel("P2", width, height, convertedPixels, 1); // Grayscale
    }
}
