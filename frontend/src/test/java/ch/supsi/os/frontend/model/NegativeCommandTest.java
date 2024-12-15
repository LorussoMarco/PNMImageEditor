package ch.supsi.os.frontend.model;

import ch.supsi.os.backend.business.ImageModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NegativeCommandTest {

    private NegativeCommand command;
    private ImageModel imageModel;

    @BeforeEach
    void setUp() {
        command = new NegativeCommand();
        imageModel = new ImageModel();

        // Configura un'ImageModel di test con pixel noti
        imageModel.setMagicNumber("P2");
        imageModel.setWidth(3);
        imageModel.setHeight(3);
        imageModel.setChannels(1);
        imageModel.setPixels(new int[][]{
                {0, 128, 255},
                {64, 192, 32},
                {255, 0, 128}
        });
    }

    @Test
    void testExecute() {
        // Applica la trasformazione negativa
        command.execute(imageModel);

        // Verifica che i pixel siano invertiti correttamente
        int[][] expectedPixels = {
                {255, 127, 0},
                {191, 63, 223},
                {0, 255, 127}
        };
        assertArrayEquals(expectedPixels, imageModel.getPixels());
    }
}
