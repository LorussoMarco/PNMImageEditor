package ch.supsi.os.backend.application;

import ch.supsi.os.backend.business.FormatConverterFactory;
import ch.supsi.os.backend.business.ImageModel;
import ch.supsi.os.backend.dataAccess.ImageHandler;
import ch.supsi.os.backend.dataAccess.PbmHandler;
import ch.supsi.os.backend.dataAccess.PgmHandler;
import ch.supsi.os.backend.dataAccess.PpmHandler;

import java.io.IOException;

public class ImageController {

    private static ImageController instance;
    private ImageModel imageModel;
    private ImageHandler handlerChain;
    private String currentImagePath;
    private final FormatConverterFactory converterFactory;

    private ImageController() {
        this.imageModel = new ImageModel();
        initializeChain();
        this.converterFactory = new FormatConverterFactory();
    }

    public static ImageController getInstance() {
        if (instance == null) {
            instance = new ImageController();
        }
        return instance;
    }

    private void initializeChain() {
        ImageHandler pbmHandler = new PbmHandler();
        ImageHandler pgmHandler = new PgmHandler();
        ImageHandler ppmHandler = new PpmHandler();

        pbmHandler.setNextHandler(pgmHandler);
        pgmHandler.setNextHandler(ppmHandler);

        this.handlerChain = pbmHandler;
    }

    public void loadImageFromFile(String filePath) throws IOException {
        try {
            handlerChain.handle(filePath, imageModel);
            currentImagePath = filePath;
        } catch (IllegalArgumentException e) {
            throw new IOException("Unsupported or invalid image format: " + filePath, e);
        } catch (IOException e) {
            throw new IOException("Error reading file: " + filePath, e);
        } catch (Exception e) {
            throw new IOException("Unexpected error occurred while loading the file: " + filePath, e);
        }
    }

    public void saveImageToFile(String filePath) throws IOException {
        if (imageModel == null) {
            throw new IllegalStateException("No image loaded to save.");
        }
        handlerChain.save(filePath, imageModel);
    }

    public void saveImageAs(String filePath, String targetMagicNumber) throws IOException {
        if (imageModel == null) {
            throw new IllegalStateException("No image loaded to save.");
        }

        var converter = converterFactory.getConverter(targetMagicNumber);
        ImageModel convertedImage = converter.convert(imageModel);

        handlerChain.save(filePath, convertedImage);
    }

    public ImageModel getImageModel() {
        return imageModel;
    }

    public String getCurrentImagePath() {
        return currentImagePath;
    }
}
