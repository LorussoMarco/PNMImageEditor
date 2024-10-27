package ch.supsi.os.backend.model;

public class ImageModel {
    private String magicNumber;
    private int width;
    private int height;
    private int[][] pixels;
    private int channels;

    public ImageModel(String magicNumber, int width, int height, int[][] pixels, int channels) {
        this.magicNumber = magicNumber;
        this.width = width;
        this.height = height;
        this.pixels = pixels;
        this.channels = channels;
    }

    public ImageModel() {
    }

    public void setMagicNumber(String magicNumber) {
        this.magicNumber = magicNumber;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setPixels(int[][] pixels) {
        this.pixels = pixels;
    }

    public int getChannels() {
        return channels;
    }

    public void setChannels(int channels) {
        this.channels = channels;
    }

    @Override
    public String toString() {
        return "PBMModel{" +
                "magicNumber='" + magicNumber + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", pixels=\n" + pixelToString();
    }

    private String pixelToString(){
        String output = "";
        for(int i = 0; i < pixels.length; i++){
            for(int j = 0; j < pixels[i].length; j++){
                output = output + " " +  pixels[i][j];
            }
            output += "\n";
        }
        return output;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getMagicNumber() {
        return magicNumber;
    }

    public int[][] getPixels() {
        return pixels;
    }

    /**
     * Flips the image upside down by reversing the order of the rows in the pixel array.
     * It creates a new array where the first row becomes the last, the second row becomes the second-to-last, and so on.
     * The flipped array replaces the original pixel data.
     */
    public void flipImageUpsideDown() {
        int[][] flippedPixels = new int[height][width * channels];
        for (int i = 0; i < height; i++) {
            flippedPixels[i] = pixels[height - 1 - i];
        }
        pixels = flippedPixels;
    }
    public void flipImageSideToSide() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width / 2; j++) {
                if (channels == 3) {
                    int leftIndex = j * 3;
                    int rightIndex = (width - 1 - j) * 3;

                    for (int k = 0; k < 3; k++) {
                        int temp = pixels[i][leftIndex + k];
                        pixels[i][leftIndex + k] = pixels[i][rightIndex + k];
                        pixels[i][rightIndex + k] = temp;
                    }
                } else {
                    int temp = pixels[i][j];
                    pixels[i][j] = pixels[i][width - 1 - j];
                    pixels[i][width - 1 - j] = temp;
                }
            }
        }
    }

    public void rotate90Clockwise() {
        int newWidth = height;
        int newHeight = width;
        int[][] rotatedPixels = new int[newHeight][newWidth * channels];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (channels == 3) {
                    // Handle RGB channels separately
                    int sourceIndex = j * 3;
                    int destinationIndex = (newWidth - 1 - i) * 3;
                    rotatedPixels[j][destinationIndex] = pixels[i][sourceIndex];
                    rotatedPixels[j][destinationIndex + 1] = pixels[i][sourceIndex + 1];
                    rotatedPixels[j][destinationIndex + 2] = pixels[i][sourceIndex + 2];
                } else {
                    rotatedPixels[j][newWidth - 1 - i] = pixels[i][j];
                }
            }
        }

        this.width = newWidth;
        this.height = newHeight;
        this.pixels = rotatedPixels;
    }

    public void rotate90AntiClockwise() {
        int newWidth = height;
        int newHeight = width;
        int[][] rotatedPixels = new int[newHeight][newWidth * channels];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (channels == 3) {
                    // Calculate correct indices for RGB channels
                    int sourceIndex = j * 3;
                    int destinationRow = width - 1 - j;
                    int destinationIndex = i * 3;

                    // Copy RGB values from the source pixel to the correct rotated position
                    rotatedPixels[destinationRow][destinationIndex] = pixels[i][sourceIndex];
                    rotatedPixels[destinationRow][destinationIndex + 1] = pixels[i][sourceIndex + 1];
                    rotatedPixels[destinationRow][destinationIndex + 2] = pixels[i][sourceIndex + 2];
                } else {
                    // If it's not an RGB image, handle as a single-channel grayscale image
                    rotatedPixels[width - 1 - j][i] = pixels[i][j];
                }
            }
        }

        // Update the image dimensions and pixels
        this.width = newWidth;
        this.height = newHeight;
        this.pixels = rotatedPixels;
    }





}
