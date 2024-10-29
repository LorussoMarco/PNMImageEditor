package ch.supsi.os.backend.dataAccess;

import ch.supsi.os.backend.business.ImageModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class AbstractImageHandlerTest {
    private TestImageHandler handler1;
    private TestImageHandler handler2;
    private TestImageHandler handler3;
    private ImageModel imageModel;

    @BeforeEach
    void setUp() {
        handler1 = new TestImageHandler("P1");
        handler2 = new TestImageHandler("P2");
        handler3 = new TestImageHandler("P3");

        // Set up chain of responsibility
        handler1.setNextHandler(handler2);
        handler2.setNextHandler(handler3);

        // Sample ImageModel setup
        imageModel = new ImageModel("P1", 5, 5, new int[5][5], 1);
    }

    @Test
    void testHandlerCanProcessSupportedFormat() throws IOException {
        handler1.handle("testPath.pbm", imageModel);
        assertTrue(handler1.wasProcessed);
        assertFalse(handler2.wasProcessed);
        assertFalse(handler3.wasProcessed);
    }

    @Test
    void testHandlerPassesUnsupportedFormatToNextHandler() throws IOException {
        imageModel.setMagicNumber("P2"); // Set to a format only handler2 can process
        handler1.handle("testPath.pgm", imageModel);
        assertFalse(handler1.wasProcessed);
        assertTrue(handler2.wasProcessed);
        assertFalse(handler3.wasProcessed);
    }

    @Test
    void testHandlerThrowsExceptionForUnsupportedFormat() {
        imageModel.setMagicNumber("P5"); // Unsupported format by any handler
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                handler1.handle("testPath.unknown", imageModel));
        assertEquals("Unsupported image format", exception.getMessage());
    }

    // Helper TestImageHandler class
    static class TestImageHandler extends AbstractImageHandler {
        private final String supportedFormat;
        boolean wasProcessed = false;

        public TestImageHandler(String supportedFormat) {
            this.supportedFormat = supportedFormat;
        }

        @Override
        protected boolean canHandle(String filePath, ImageModel imageModel) {
            return supportedFormat.equals(imageModel.getMagicNumber());
        }

        @Override
        protected void process(String filePath, ImageModel imageModel) {
            wasProcessed = true; // Mark as processed for test verification
        }
    }
}
