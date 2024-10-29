package ch.supsi.os.backend.business;

public class FlipUpsideDownTransformation implements ImageTransformationStrategy {

    @Override
    public void applyTransformation(ImageModel imageModel) {
        int[][] flippedPixels = new int[imageModel.getHeight()][imageModel.getWidth() * imageModel.getChannels()];
        for (int i = 0; i < imageModel.getHeight(); i++) {
            flippedPixels[i] = imageModel.getPixels()[imageModel.getHeight() - 1 - i];
        }
        imageModel.setPixels(flippedPixels);
    }
}
