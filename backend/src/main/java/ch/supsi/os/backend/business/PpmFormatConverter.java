package ch.supsi.os.backend.business;

public class PpmFormatConverter implements FormatConverter {

    @Override
    public boolean supports(String magicNumber) {
        return "P3".equals(magicNumber);
    }

    @Override
    public ImageModel convert(ImageModel sourceImage) {
        if (!sourceImage.getMagicNumber().equals("P1") &&
                !sourceImage.getMagicNumber().equals("P2") &&
                !sourceImage.getMagicNumber().equals("P3")) {
            throw new IllegalArgumentException("Unsupported source image format for conversion to PPM.");
        }

        if (sourceImage.getMagicNumber().equals("P3")) {
            return sourceImage; // Already in PPM format
        }

        int width = sourceImage.getWidth();
        int height = sourceImage.getHeight();
        int[][] originalPixels = sourceImage.getPixels();
        int[][] convertedPixels = new int[height][width * 3];

        // Convert grayscale or binary pixels to RGB
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int grayValue = originalPixels[y][x];
                if (sourceImage.getMagicNumber().equals("P1")) {
                    // Scale binary (0 or 1) to RGB (0 or 255)
                    grayValue *= 255;
                }
                int index = x * 3; // Each pixel now occupies 3 slots (R, G, B)
                convertedPixels[y][index] = grayValue;   // Red
                convertedPixels[y][index + 1] = grayValue; // Green
                convertedPixels[y][index + 2] = grayValue; // Blue
            }
        }

        return new ImageModel("P3", width, height, convertedPixels, 3); // RGB
    }
}
