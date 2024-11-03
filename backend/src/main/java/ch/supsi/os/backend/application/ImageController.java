package ch.supsi.os.backend.application;
import javafx.stage.FileChooser;

import ch.supsi.os.backend.dataAccess.PbmHandler;
import ch.supsi.os.backend.dataAccess.PgmHandler;
import ch.supsi.os.backend.dataAccess.PpmHandler;
import ch.supsi.os.backend.business.ImageModel;
import ch.supsi.os.backend.dataAccess.ImageHandler;

import java.io.File;
import java.io.IOException;

public class ImageController {

    private static ImageController instance;
    private ImageModel imageModel;
    private ImageHandler handlerChain;

    private ImageController() {
        this.imageModel = new ImageModel();
        initializeChain();
    }

    public static ImageController getInstance() {
        if (instance == null) {
            instance = new ImageController();
        }
        return instance;
    }

    private void initializeChain() {
        // Create individual handlers
        ImageHandler pbmHandler = new PbmHandler();
        ImageHandler pgmHandler = new PgmHandler();
        ImageHandler ppmHandler = new PpmHandler();

        // Set up the chain
        pbmHandler.setNextHandler(pgmHandler);
        pgmHandler.setNextHandler(ppmHandler);

        this.handlerChain = pbmHandler; // Start of the chain
    }

    public void loadImageFromFile(String filePath) throws IOException {
        // Delegate to chain
        handlerChain.handle(filePath, imageModel);
    }

    public void saveImageToFile(String filePath) throws IOException {
        if (imageModel == null) {
            throw new IllegalStateException("No image loaded to save.");
        }

        ImageHandler handler = getImageHandlerChain();
        handler.save(filePath, imageModel);
    }

    private ImageHandler getImageHandlerChain() {
        ImageHandler pbmHandler = new PbmHandler();
        ImageHandler pgmHandler = new PgmHandler();
        ImageHandler ppmHandler = new PpmHandler();

        pbmHandler.setNextHandler(pgmHandler);
        pgmHandler.setNextHandler(ppmHandler);

        return pbmHandler; // Start of the chain
    }

    public ImageModel getImageModel() {
        return imageModel;
    }
}
