package ch.supsi.os.backend.dataAccess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class PNMImageReader {

    private BufferedReader reader;

    public PNMImageReader(String filePath) throws IOException {
        File file = new File(filePath);

        if (!file.exists() || !file.canRead()) {
            throw new IOException("File cannot be read or does not exist: " + filePath);
        }

        this.reader = new BufferedReader(new FileReader(file));
    }

    public String getMagicNumber() throws IOException {
        return reader.readLine().trim();
    }

    public int[] getDimensions() throws IOException {
        String line;
        while ((line = reader.readLine()) != null && line.startsWith("#")) {
            // Skip comments
        }

        if (line == null || line.trim().isEmpty()) {
            throw new IOException("Invalid PNM file: missing image dimensions.");
        }

        String[] dimensions = line.trim().split("\\s+");
        if (dimensions.length != 2) {
            throw new IOException("Invalid PNM file: image dimensions are incorrect.");
        }

        return new int[] { Integer.parseInt(dimensions[0]), Integer.parseInt(dimensions[1]) };
    }

    public int getMaxGrayValue(String magicNumber) throws IOException {
        if (magicNumber.equals("P2") || magicNumber.equals("P3")) {
            return Integer.parseInt(reader.readLine().trim());
        }
        return 255;
    }

    /**
     * Reads the pixel data from a PNM file and stores it in a two-dimensional array.
     * The number of channels (1 for P1/P2 and 3 for P3) is determined based on the "magic number" of the file.
     *
     * @param magicNumber The "magic number" that identifies the PNM file format (P1, P2, or P3).
     * @param width The width of the image in pixels.
     * @param height The height of the image in pixels.
     * @param maxGrayValue The maximum gray value for grayscale images (only used for P2).
     * @return A two-dimensional array containing the pixel values of the image.
     * @throws IOException If the number of pixel values does not match the expected width and height of the image.
     */
    public int[][] readPixels(String magicNumber, int width, int height, int maxGrayValue) throws IOException {
        int channels = magicNumber.equals("P3") ? 3 : 1;
        int[][] pixels = new int[height][width * channels];

        for (int i = 0; i < height; i++) {
            String line = reader.readLine().trim();
            String[] pixelValues = line.split("\\s+");

            if (pixelValues.length != width * channels) {
                throw new IOException("Pixel data width mismatch: expected " + (width * channels) + " values, but got " + pixelValues.length);
            }

            for (int j = 0; j < width * channels; j++) {
                int pixelValue = Integer.parseInt(pixelValues[j]);
                pixels[i][j] = normalizePixelValue(magicNumber, pixelValue, maxGrayValue);
            }
        }

        return pixels;
    }

    /**
     * Normalizes the pixel value based on the image format (magic number).
     * - For P1 (black and white), returns 255 for a pixel value of 1 (white) and 0 for a pixel value of 0 (black).
     * - For P2 (grayscale), normalizes the pixel value to a 0-255 range using the maxGrayValue.
     * - For P3 (RGB), no normalization is required as the pixel values are already in the correct range.
     *
     * @param magicNumber The "magic number" that identifies the PNM file format (P1, P2, or P3).
     * @param pixelValue The raw pixel value from the file.
     * @param maxGrayValue The maximum gray value for grayscale images (only used for P2).
     * @return The normalized pixel value based on the format.
     */
    private int normalizePixelValue(String magicNumber, int pixelValue, int maxGrayValue) {
        if (magicNumber.equals("P1")) {
            return pixelValue == 1 ? 255 : 0;
        } else if (magicNumber.equals("P2")) {
            return (pixelValue * 255) / maxGrayValue;
        }
        return pixelValue;
    }

    public void close() throws IOException {
        if (reader != null) {
            reader.close();
        }
    }
}
