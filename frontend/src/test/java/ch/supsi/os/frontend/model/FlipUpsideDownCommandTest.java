package ch.supsi.os.frontend.model;

import ch.supsi.os.backend.business.ImageModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlipUpsideDownCommandTest {

    private FlipUpsideDownCommand command;
    private ImageModel imageModel;

    @BeforeEach
    void setUp() {
        command = new FlipUpsideDownCommand();
        imageModel = new ImageModel();

        // Configura un'immagine di test con pixel noti
        imageModel.setMagicNumber("P2");
        imageModel.setWidth(3);
        imageModel.setHeight(3);
        imageModel.setChannels(1);
        imageModel.setPixels(new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        });
    }

    @Test
    void testExecute() {
        // Applica la trasformazione di flip upside-down
        command.execute(imageModel);

        // Verifica che i pixel siano stati invertiti correttamente
        int[][] expectedPixels = {
                {7, 8, 9},
                {4, 5, 6},
                {1, 2, 3}
        };
        assertArrayEquals(expectedPixels, imageModel.getPixels());
    }

    @Test
    void testGetDescription() {
        // Verifica la descrizione del comando
        String description = command.getDescription();
        assertEquals("Flip upside-down applied", description);
    }
}
