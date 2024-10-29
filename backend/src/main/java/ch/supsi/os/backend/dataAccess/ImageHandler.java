package ch.supsi.os.backend.dataAccess;

import ch.supsi.os.backend.business.ImageModel;

import java.io.IOException;

public interface ImageHandler {
    void setNextHandler(ImageHandler handler);
    void handle(String filePath, ImageModel imageModel) throws IOException;
}
