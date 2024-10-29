package ch.supsi.os.backend.business;

public class NegativeTransformation implements ImageTransformationStrategy {

    @Override
    public void applyTransformation(ImageModel imageModel) {
        int[][] pixels = imageModel.getPixels();
        int width = imageModel.getWidth();
        int height = imageModel.getHeight();
        int channels = imageModel.getChannels();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (channels == 3) {
                    int index = j * 3;
                    if (index + 2 < pixels[i].length) {
                        pixels[i][index] = 255 - pixels[i][index];
                        pixels[i][index + 1] = 255 - pixels[i][index + 1];
                        pixels[i][index + 2] = 255 - pixels[i][index + 2];
                    }
                } else {
                    pixels[i][j] = 255 - pixels[i][j];
                }
            }
        }
    }
}
