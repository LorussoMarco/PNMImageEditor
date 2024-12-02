package ch.supsi.os.frontend.controller;

import ch.supsi.os.backend.business.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransformationPipelineControllerTest {
    private TransformationPipelineController pipelineController;
    private ImageModel imageModel;
    private LocalizationController localizationController;

    @BeforeEach
    void setUp() {
        pipelineController = TransformationPipelineController.getInstance();
        pipelineController.clearPipeline(); // Ensure the pipeline is empty before each test
        imageModel = new ImageModel();
        localizationController = LocalizationController.getInstance();
    }

    @Test
    void testAddTransformation() {
        FlipUpsideDownTransformation transformation = new FlipUpsideDownTransformation();
        pipelineController.addTransformation(transformation);

        // Recupera il nome localizzato della trasformazione
        String localizedName = localizationController.getLocalizedText("transformation.flipupsidedown");
        String expectedDescription = localizedName + "\n";

        assertEquals(expectedDescription, pipelineController.getPipelineDescription());
    }

    @Test
    void testApplyPipeline() {
        // Aggiungi trasformazioni multiple
        FlipUpsideDownTransformation transformation1 = new FlipUpsideDownTransformation();
        FlipSideToSideTransformation transformation2 = new FlipSideToSideTransformation();
        pipelineController.addTransformation(transformation1);
        pipelineController.addTransformation(transformation2);

        // Applica la pipeline sull'immagine
        pipelineController.applyPipeline(imageModel);

        // Non verifichiamo direttamente l'immagine, ma possiamo verificare che la pipeline sia ancora descritta correttamente
        String expectedDescription = localizationController.getLocalizedText("transformation.flipupsidedown") + "\n" +
                localizationController.getLocalizedText("transformation.flipsidetoside") + "\n";

        assertEquals(expectedDescription, pipelineController.getPipelineDescription());
    }

    @Test
    void testClearPipeline() {
        FlipUpsideDownTransformation transformation = new FlipUpsideDownTransformation();
        pipelineController.addTransformation(transformation);

        // Svuota la pipeline
        pipelineController.clearPipeline();

        // Verifica che la descrizione sia vuota
        assertTrue(pipelineController.getPipelineDescription().isEmpty(), "Pipeline description should be empty after clearing");
    }

    @Test
    void testGetPipelineDescription() {
        Rotate90ClockwiseTransformation transformation1 = new Rotate90ClockwiseTransformation();
        Rotate90AntiClockwiseTransformation transformation2 = new Rotate90AntiClockwiseTransformation();
        pipelineController.addTransformation(transformation1);
        pipelineController.addTransformation(transformation2);

        // Recupera la descrizione localizzata
        String expectedDescription = localizationController.getLocalizedText("transformation.rotateclockwise") + "\n" +
                localizationController.getLocalizedText("transformation.rotateanticlockwise") + "\n";

        assertEquals(expectedDescription, pipelineController.getPipelineDescription());
    }
}
