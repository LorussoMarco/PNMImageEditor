package ch.supsi.os.backend.dataAccess;

import ch.supsi.os.backend.business.ImageModel;

import java.io.*;

public class PpmHandler extends AbstractImageHandler {

    @Override
    protected boolean canHandle(String filePath, ImageModel imageModel) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String magicNumber = reader.readLine().trim();
            return "P3".equals(magicNumber);
        }
    }

    @Override
    protected void process(String filePath, ImageModel imageModel) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine();

            String line;
            do {
                line = reader.readLine().trim();
            } while (line.startsWith("#"));

            String[] dimensions = line.split("\\s+");
            int width = Integer.parseInt(dimensions[0]);
            int height = Integer.parseInt(dimensions[1]);

            do {
                line = reader.readLine().trim();
            } while (line.startsWith("#"));

            int maxColorValue = Integer.parseInt(line);

            int[][] pixels = new int[height][width * 3];

            int rowIndex = 0;
            while ((line = reader.readLine()) != null && rowIndex < height) {
                if (line.startsWith("#")) continue;

                String[] pixelValues = line.trim().split("\\s+");
                for (int colIndex = 0; colIndex < width * 3 && colIndex < pixelValues.length; colIndex++) {
                    int colorValue = Integer.parseInt(pixelValues[colIndex]);
                    // Normalize to 0-255 range
                    pixels[rowIndex][colIndex] = (colorValue * 255) / maxColorValue;
                }
                rowIndex++;
            }

            imageModel.setMagicNumber("P3");
            imageModel.setWidth(width);
            imageModel.setHeight(height);
            imageModel.setChannels(3);
            imageModel.setPixels(pixels);
        }
    }

    @Override
    public void save(String filePath, ImageModel imageModel) throws IOException {
        if (!"P3".equals(imageModel.getMagicNumber())) {
            if (nextHandler != null) {
                nextHandler.save(filePath, imageModel);
            } else {
                throw new IllegalArgumentException("No handler available for format: " + imageModel.getMagicNumber());
            }
            return;
        }

        // Proceed with saving in PPM format
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("P3\n");
            writer.write(imageModel.getWidth() + " " + imageModel.getHeight() + "\n");
            writer.write("255\n");

            int[][] pixels = imageModel.getPixels();
            for (int[] row : pixels) {
                for (int pixel : row) {
                    writer.write(pixel + " ");
                }
                writer.newLine();
            }
        }
    }

}
