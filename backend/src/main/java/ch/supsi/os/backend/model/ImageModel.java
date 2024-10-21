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
}
