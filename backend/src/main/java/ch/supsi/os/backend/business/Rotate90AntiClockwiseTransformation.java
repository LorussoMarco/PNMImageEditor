package ch.supsi.os.backend.business;

public class Rotate90AntiClockwiseTransformation implements ImageTransformationStrategy {

    @Override
    public void applyTransformation(ImageModel imageModel) {
        int newWidth = imageModel.getHeight();
        int newHeight = imageModel.getWidth();
        int channels = imageModel.getChannels();
        int[][] rotatedPixels = new int[newHeight][newWidth * channels];

        int[][] pixels = imageModel.getPixels();

        for (int i = 0; i < imageModel.getHeight(); i++) {
            for (int j = 0; j < imageModel.getWidth(); j++) {
                if (channels == 3) {
                    int sourceIndex = j * 3;
                    int destinationRow = imageModel.getWidth() - 1 - j;
                    int destinationIndex = i * 3;

                    // Copy RGB values to the correct rotated position
                    rotatedPixels[destinationRow][destinationIndex] = pixels[i][sourceIndex];
                    rotatedPixels[destinationRow][destinationIndex + 1] = pixels[i][sourceIndex + 1];
                    rotatedPixels[destinationRow][destinationIndex + 2] = pixels[i][sourceIndex + 2];
                } else {
                    // Single channel (grayscale)
                    rotatedPixels[imageModel.getWidth() - 1 - j][i] = pixels[i][j];
                }
            }
        }

        // Update the image model with new dimensions and rotated pixel array
        imageModel.setWidth(newWidth);
        imageModel.setHeight(newHeight);
        imageModel.setPixels(rotatedPixels);
    }
}
