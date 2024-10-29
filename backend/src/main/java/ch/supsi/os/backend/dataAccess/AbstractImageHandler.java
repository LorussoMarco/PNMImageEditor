package ch.supsi.os.backend.dataAccess;

import ch.supsi.os.backend.business.ImageModel;

import java.io.IOException;

public abstract class AbstractImageHandler implements ImageHandler {
    protected ImageHandler nextHandler;

    @Override
    public void setNextHandler(ImageHandler handler) {
        this.nextHandler = handler;
    }

    @Override
    public void handle(String filePath, ImageModel imageModel) throws IOException {
        if (canHandle(filePath, imageModel)) {
            process(filePath, imageModel);
        } else if (nextHandler != null) {
            nextHandler.handle(filePath, imageModel);
        } else {
            throw new IllegalArgumentException("Unsupported image format");
        }
    }

    protected abstract boolean canHandle(String filePath, ImageModel imageModel) throws IOException;

    protected abstract void process(String filePath, ImageModel imageModel) throws IOException;
}
