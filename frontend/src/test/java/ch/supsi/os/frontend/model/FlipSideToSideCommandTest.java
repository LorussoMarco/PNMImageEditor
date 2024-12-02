package ch.supsi.os.frontend.model;

import ch.supsi.os.backend.business.ImageModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlipSideToSideCommandTest {

    private FlipSideToSideCommand command;
    private ImageModel imageModel;

    @BeforeEach
    void setUp() {
        command = new FlipSideToSideCommand();
        imageModel = new ImageModel();

        // Configura un'immagine di test con pixel noti
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
        // Applica la trasformazione di flip side-to-side
        command.execute(imageModel);

        // Verifica che i pixel siano stati invertiti correttamente
        int[][] expectedPixels = {
                {3, 2, 1},
                {6, 5, 4}
        };
        assertArrayEquals(expectedPixels, imageModel.getPixels());
    }

    @Test
    void testGetDescription() {
        // Verifica la descrizione del comando
        String description = command.getDescription();
        assertEquals("Flip side to side applied", description);
    }
}
