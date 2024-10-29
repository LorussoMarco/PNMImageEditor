package ch.supsi.os.backend.dataAccess;

import ch.supsi.os.backend.business.ImageModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PbmHandlerTest {
    private PbmHandler pbmHandler;
    private ImageModel imageModel;

    @BeforeEach
    void setUp() {
        pbmHandler = new PbmHandler();
        imageModel = new ImageModel();
    }

    @Test
    void testCanHandleValidPbmFile() throws IOException {
        File tempFile = createTempPbmFile("P1\n3 3\n1 0 1\n0 1 0\n1 0 1");
        assertTrue(pbmHandler.canHandle(tempFile.getPath(), imageModel));
        tempFile.delete();
    }

    @Test
    void testCannotHandleNonPbmFile() throws IOException {
        File tempFile = createTempPbmFile("P2\n3 3\n1 0 1\n0 1 0\n1 0 1");
        assertFalse(pbmHandler.canHandle(tempFile.getPath(), imageModel));
        tempFile.delete();
    }

    @Test
    void testProcessValidPbmFile() throws IOException {
        File tempFile = createTempPbmFile("P1\n# This is a comment\n3 3\n1 0 1\n0 1 0\n1 0 1");
        pbmHandler.process(tempFile.getPath(), imageModel);

        assertEquals("P1", imageModel.getMagicNumber());
        assertEquals(3, imageModel.getWidth());
        assertEquals(3, imageModel.getHeight());
        assertEquals(1, imageModel.getChannels());

        int[][] expectedPixels = {
                {255, 0, 255},
                {0, 255, 0},
                {255, 0, 255}
        };
        assertArrayEquals(expectedPixels, imageModel.getPixels());

        tempFile.delete();
    }

    @Test
    void testProcessPbmFileWithComments() throws IOException {
        File tempFile = createTempPbmFile("P1\n# Comment line\n3 3\n1 1 1\n0 0 0\n1 1 1");
        pbmHandler.process(tempFile.getPath(), imageModel);

        assertEquals("P1", imageModel.getMagicNumber());
        assertEquals(3, imageModel.getWidth());
        assertEquals(3, imageModel.getHeight());

        int[][] expectedPixels = {
                {255, 255, 255},
                {0, 0, 0},
                {255, 255, 255}
        };
        assertArrayEquals(expectedPixels, imageModel.getPixels());

        tempFile.delete();
    }

    private File createTempPbmFile(String content) throws IOException {
        File tempFile = File.createTempFile("test", ".pbm");
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(content);
        }
        return tempFile;
    }
}
