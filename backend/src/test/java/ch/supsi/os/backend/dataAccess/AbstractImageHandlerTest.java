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

        handler1.setNextHandler(handler2);
        handler2.setNextHandler(handler3);

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
        imageModel.setMagicNumber("P2");
        handler1.handle("testPath.pgm", imageModel);
        assertFalse(handler1.wasProcessed);
        assertTrue(handler2.wasProcessed);
        assertFalse(handler3.wasProcessed);
    }

    @Test
    void testHandlerThrowsExceptionForUnsupportedFormat() {
        imageModel.setMagicNumber("P5");
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                handler1.handle("testPath.unknown", imageModel));
        assertEquals("Unsupported image format", exception.getMessage());
    }

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
            wasProcessed = true;
        }

        @Override
        public void save(String filePath, ImageModel imageModel) throws IOException {

        }
    }
}
