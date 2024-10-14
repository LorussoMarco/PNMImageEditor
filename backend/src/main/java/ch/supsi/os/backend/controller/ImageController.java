package ch.supsi.os.backend.controller;
import ch.supsi.os.backend.model.ImageModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;

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
        File file = new File(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String magicNumber = reader.readLine().trim();
        int maxGrayValue = 255;

        String line;
        while ((line = reader.readLine()) != null && line.startsWith("#")) {
            // Skip comment lines
        }

        // Read width and height
        String[] dimensions = line.trim().split("\\s+");
        int width = Integer.parseInt(dimensions[0]);
        int height = Integer.parseInt(dimensions[1]);

        if (magicNumber.equals("P2") || magicNumber.equals("P3")) {
            line = reader.readLine().trim();
            maxGrayValue = Integer.parseInt(line);
        }

        int channels = 1;
        switch(magicNumber) {
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

        int[][] pixels = new int[height][width];
        for (int i = 0; i < height; i++) {
            String[] pixelValues = reader.readLine().trim().split("\\s+");
            for (int j = 0; j < width; j++) {
                int pixelValue = Integer.parseInt(pixelValues[j]);

                if (magicNumber.equals("P1")) {
                    pixels[i][j] = (pixelValue == 1) ? 255 : 0;
                } else if (magicNumber.equals("P2")) {
                    pixels[i][j] = (pixelValue * 255) / maxGrayValue;
                } else if (magicNumber.equals("P3")) {
                    pixels[i][j] = pixelValue;
                }
            }
        }

        reader.close();

        imageModel = new ImageModel(magicNumber, width / channels, height, pixels, channels);
        System.out.println(imageModel.toString());
    }

    public ImageModel getImageModel() {
        return imageModel;
    }
}
