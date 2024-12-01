package ch.supsi.os.backend.business;

public class PbmFormatConverter implements FormatConverter {


    private static final int DEFAULT_THRESHOLD = 128;
    private int threshold;

    public PbmFormatConverter() {
        this.threshold = DEFAULT_THRESHOLD;
    }

    public PbmFormatConverter(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public boolean supports(String magicNumber) {
        return "P1".equals(magicNumber);
    }

    @Override
    public ImageModel convert(ImageModel sourceImage) {
        if (sourceImage.getMagicNumber().equals("P1")) {
            return sourceImage; // Already in PBM format
        }

        int width = sourceImage.getWidth();
        int height = sourceImage.getHeight();
        int[][] originalPixels = sourceImage.getPixels();
        int[][] convertedPixels = new int[height][width];

        // Debugging the input pixels
        System.out.println("Original Pixels:");
        for (int[] row : originalPixels) {
            for (int pixel : row) {
                System.out.print(pixel + " ");
            }
            System.out.println();
        }

        if (sourceImage.getMagicNumber().equals("P2") && sourceImage.getChannels() == 1) {
            // Convert PGM to PBM
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int pixelValue = originalPixels[y][x];
                    convertedPixels[y][x] = (pixelValue >= threshold) ? 1 : 0;
                }
            }
        } else if (sourceImage.getMagicNumber().equals("P3") && sourceImage.getChannels() == 3) {
            // Convert PPM to PBM
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int r = originalPixels[y][x * 3];
                    int g = originalPixels[y][x * 3 + 1];
                    int b = originalPixels[y][x * 3 + 2];

                    // Weighted grayscale conversion
                    int grayscale = (int) Math.round(0.299 * r + 0.587 * g + 0.114 * b);
                    convertedPixels[y][x] = (grayscale >= threshold) ? 1 : 0;
                }
            }
        } else {
            throw new IllegalArgumentException("Unsupported source image format for conversion to PBM.");
        }

        return new ImageModel("P1", width, height, convertedPixels, 1); // PBM has 1 channel
    }
}
