package ch.supsi.os.backend.application;

import ch.supsi.os.backend.dataAccess.PNMImageReader;
import ch.supsi.os.backend.model.ImageModel;

import java.io.IOException;

public class ImageController {

    private static ImageController myself;
    private static ImageModel imageModel;

    private ImageController() {}

    public static ImageController getInstance() {
        if (myself == null) {
            myself = new ImageController();
        }
        return myself;
    }

    public void loadImageFromFile(String filePath) throws IOException {
        PNMImageReader reader = new PNMImageReader(filePath);
        String magicNumber = reader.getMagicNumber();
        int[] dimensions = reader.getDimensions();
        int maxGrayValue = reader.getMaxGrayValue(magicNumber);

        int width = dimensions[0];
        int height = dimensions[1];

        int channels = 1;
        switch (magicNumber) {
            case "P1":
                channels = 1;
                break;
            case "P2":
                channels = 1;
                break;
            case "P3":
                channels = 3;
                width *= 3;
                break;
            default:
                throw new IllegalArgumentException("Unsupported format: " + magicNumber);
        }

        int[][] pixels = reader.readPixels(magicNumber, width / channels, height, maxGrayValue);

        reader.close();

        imageModel = new ImageModel(magicNumber, width / channels, height, pixels, channels);
    }

    public ImageModel getImageModel() {
        return imageModel;
    }
}
