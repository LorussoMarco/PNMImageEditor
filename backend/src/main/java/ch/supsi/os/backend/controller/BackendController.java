package ch.supsi.os.backend.controller;

import java.io.IOException;

public class BackendController {
    private static ImageController pbmController = ImageController.getInstance();

    public static void main(String[] args) {
        try {
            pbmController.loadImageFromFile("C:\\scuola\\SUPSI\\ANNO 3\\1 semestre\\ingegneria del software 2\\os\\backend\\src\\main\\java\\ch\\supsi\\os\\backend\\controller\\ppm.ppm");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
