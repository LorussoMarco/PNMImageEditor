package ch.supsi.os.frontend.model;

import ch.supsi.os.backend.business.ImageModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Rotate90ClockwiseCommandTest {

    private Rotate90ClockwiseCommand command;
    private ImageModel imageModel;

    @BeforeEach
    void setUp() {
        command = new Rotate90ClockwiseCommand();
        imageModel = new ImageModel();

        // Configura un'ImageModel di test con una matrice di pixel nota
        imageModel.setMagicNumber("P2");
        imageModel.setWidth(3);
        imageModel.setHeight(2);
        imageModel.setChannels(1);
        imageModel.setPixels(new int[][]{
                {1, 2, 3},
                {4, 5, 6}
        });
    }

    @Test
    void testExecute() {
        // Applica la trasformazione di rotazione
        command.execute(imageModel);

        // Verifica che i pixel siano ruotati correttamente di 90° in senso orario
        int[][] expectedPixels = {
                {4, 1},
                {5, 2},
                {6, 3}
        };

        assertEquals(2, imageModel.getWidth());
        assertEquals(3, imageModel.getHeight());
        assertArrayEquals(expectedPixels, imageModel.getPixels());
    }

    @Test
    void testGetDescription() {
        // Verifica la descrizione del comando
        String description = command.getDescription();
        assertEquals("Rotate 90 clockwise applied", description);
    }
}
