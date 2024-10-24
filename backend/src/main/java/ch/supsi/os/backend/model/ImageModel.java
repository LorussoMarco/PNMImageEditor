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
}
