package ch.supsi.os.backend.business;
public class FlipSideToSideTransformation implements ImageTransformationStrategy {

    @Override
    public void applyTransformation(ImageModel imageModel) {
        int[][] pixels = imageModel.getPixels();
        int width = imageModel.getWidth();
        int height = imageModel.getHeight();
        int channels = imageModel.getChannels();

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
        imageModel.setPixels(pixels);
    }
}
