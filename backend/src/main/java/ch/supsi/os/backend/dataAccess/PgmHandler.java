package ch.supsi.os.backend.dataAccess;

import ch.supsi.os.backend.business.ImageModel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PgmHandler extends AbstractImageHandler {

    @Override
    protected boolean canHandle(String filePath, ImageModel imageModel) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String magicNumber = reader.readLine().trim();
            return "P2".equals(magicNumber);
        }
    }

    @Override
    protected void process(String filePath, ImageModel imageModel) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Skip magic number line
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

            int maxGrayValue = Integer.parseInt(line);

            int[][] pixels = new int[height][width];

            int rowIndex = 0;
            while ((line = reader.readLine()) != null && rowIndex < height) {
                if (line.startsWith("#")) continue;

                String[] pixelValues = line.trim().split("\\s+");
                for (int colIndex = 0; colIndex < width && colIndex < pixelValues.length; colIndex++) {
                    int pixelValue = Integer.parseInt(pixelValues[colIndex]);
                    // Normalize to 0-255 range
                    pixels[rowIndex][colIndex] = (pixelValue * 255) / maxGrayValue;
                }
                rowIndex++;
            }

            imageModel.setMagicNumber("P2");
            imageModel.setWidth(width);
            imageModel.setHeight(height);
            imageModel.setChannels(1);
            imageModel.setPixels(pixels);
        }
    }
}
