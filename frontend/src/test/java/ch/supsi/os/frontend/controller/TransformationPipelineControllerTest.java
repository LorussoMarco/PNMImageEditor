package ch.supsi.os.frontend.controller;

import ch.supsi.os.backend.business.ImageModel;
import ch.supsi.os.backend.business.ImageTransformationStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class TransformationPipelineControllerTest {
    private TransformationPipelineController pipelineController;
    private ImageModel imageModel;

    @BeforeEach
    void setUp() {
        pipelineController = TransformationPipelineController.getInstance();
        pipelineController.clearPipeline(); // Ensure the pipeline is empty before each test
        imageModel = new ImageModel();
    }

    @Test
    void testAddTransformation() {
        MockTransformation transformation = new MockTransformation();
        pipelineController.addTransformation(transformation);

        assertEquals("MockTransformation\n", pipelineController.getPipelineDescription());
    }

    @Test
    void testApplyPipeline() {
        // Create multiple mock transformations and add them to the pipeline
        MockTransformation transformation1 = new MockTransformation();
        MockTransformation transformation2 = new MockTransformation();
        pipelineController.addTransformation(transformation1);
        pipelineController.addTransformation(transformation2);

        // Apply the pipeline on the ImageModel
        pipelineController.applyPipeline(imageModel);

        // Verify that the transformations were applied
        assertTrue(transformation1.isApplied);
        assertTrue(transformation2.isApplied);
        assertTrue(pipelineController.getPipelineDescription().isEmpty(), "Pipeline should be empty after application");
    }

    @Test
    void testClearPipeline() {
        MockTransformation transformation = new MockTransformation();
        pipelineController.addTransformation(transformation);

        pipelineController.clearPipeline();
        assertTrue(pipelineController.getPipelineDescription().isEmpty(), "Pipeline description should be empty after clearing");
    }

    @Test
    void testGetPipelineDescription() {
        MockTransformation transformation1 = new MockTransformation();
        MockTransformation transformation2 = new MockTransformation();
        pipelineController.addTransformation(transformation1);
        pipelineController.addTransformation(transformation2);

        String expectedDescription = "MockTransformation\nMockTransformation\n";
        assertEquals(expectedDescription, pipelineController.getPipelineDescription());
    }

    private static class MockTransformation implements ImageTransformationStrategy {
        boolean isApplied = false;

        @Override
        public void applyTransformation(ImageModel imageModel) {
            isApplied = true;
        }
    }
}
