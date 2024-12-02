package ch.supsi.os.frontend.model;

import ch.supsi.os.backend.business.ImageModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Rotate90AntiClockwiseCommandTest {

    private Rotate90AntiClockwiseCommand command;
    private ImageModel imageModel;

    @BeforeEach
    void setUp() {
        command = new Rotate90AntiClockwiseCommand();
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

        // Verifica che i pixel siano ruotati correttamente di 90° in senso antiorario
        int[][] expectedPixels = {
                {3, 6},
                {2, 5},
                {1, 4}
        };

        assertEquals(2, imageModel.getWidth());
        assertEquals(3, imageModel.getHeight());
        assertArrayEquals(expectedPixels, imageModel.getPixels());
    }

    @Test
    void testGetDescription() {
        // Verifica la descrizione del comando
        String description = command.getDescription();
        assertEquals("Rotate 90 anticlockwise applied", description);
    }
}
