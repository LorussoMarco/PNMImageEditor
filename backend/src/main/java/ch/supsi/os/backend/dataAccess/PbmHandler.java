package ch.supsi.os.backend.dataAccess;

import ch.supsi.os.backend.business.ImageModel;

import java.io.*;

public class PbmHandler extends AbstractImageHandler {

    @Override
    protected boolean canHandle(String filePath, ImageModel imageModel) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String magicNumber = reader.readLine().trim();
            return "P1".equals(magicNumber);
        }
    }

    @Override
    protected void process(String filePath, ImageModel imageModel) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Skip the magic number line
            reader.readLine();

            String line;
            do {
                line = reader.readLine().trim();
            } while (line.startsWith("#"));

            String[] dimensions = line.split("\\s+");
            int width = Integer.parseInt(dimensions[0]);
            int height = Integer.parseInt(dimensions[1]);

            int[][] pixels = new int[height][width];

            int rowIndex = 0;
            while ((line = reader.readLine()) != null && rowIndex < height) {
                if (line.startsWith("#")) continue;

                String[] pixelValues = line.trim().split("\\s+");
                for (int colIndex = 0; colIndex < width && colIndex < pixelValues.length; colIndex++) {
                    pixels[rowIndex][colIndex] = pixelValues[colIndex].equals("1") ? 255 : 0;
                }
                rowIndex++;
            }

            imageModel.setMagicNumber("P1");
            imageModel.setWidth(width);
            imageModel.setHeight(height);
            imageModel.setChannels(1);
            imageModel.setPixels(pixels);
        }
    }

    @Override
    public void save(String filePath, ImageModel imageModel) throws IOException {
        if (!imageModel.getMagicNumber().equals("P1")) {
            if (nextHandler != null) {
                nextHandler.save(filePath, imageModel);
            } else {
                throw new UnsupportedOperationException("Unsupported format for saving.");
            }
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("P1");
            writer.newLine();
            writer.write(imageModel.getWidth() + " " + imageModel.getHeight());
            writer.newLine();

            int[][] pixels = imageModel.getPixels();
            for (int[] row : pixels) {
                for (int pixel : row) {
                    writer.write(pixel + " ");
                }
                writer.newLine();
            }

            // Ensure buffer is flushed
            writer.flush();
        }
    }

}
