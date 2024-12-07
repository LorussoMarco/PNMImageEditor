package ch.supsi.os.frontend;

import ch.supsi.os.backend.application.ImageController;
import ch.supsi.os.backend.business.ImageModel;
import ch.supsi.os.frontend.controller.ImageEventHandler;
import ch.supsi.os.frontend.controller.LocalizationController;
import ch.supsi.os.frontend.view.ImageViewFxml;
import javafx.stage.FileChooser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testfx.matcher.control.TextInputControlMatchers;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Objects;

import static org.mockito.Mockito.when;
import static org.testfx.api.FxAssert.verifyThat;

public class TransaformationTest extends AbstractMainGUITest{


    //get url from resources folder
    private final String imageURL = Paths.get((getClass().getClassLoader().getResource("lena.ppm")).toURI()).toString();
    LocalizationController localizationController;
    ImageEventHandler imageEventHandler;

    public TransaformationTest() throws URISyntaxException {

    }

    @BeforeEach
    public void setup() throws IOException {
        try {
            ImageController.getInstance().loadImageFromFile(imageURL);
            ImageModel imageModel = ImageController.getInstance().getImageModel();
            ImageViewFxml.getInstance().drawImage(imageModel);
            ImageEventHandler.notifyImageLoadedListeners();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        localizationController = LocalizationController.getInstance();
    }

    @Test
    public void walkThrough() {
        testImageTransformation();

    }

    private void testImageTransformation() {
        step("image transformations", () -> {
            // open menu
            sleep(SLEEP_INTERVAL);
            clickOn("#clearLogButton");
            sleep(SLEEP_INTERVAL);
            clickOn("#bFlipUpDown");
            sleep(SLEEP_INTERVAL);
            verifyThat("#logTextArea", TextInputControlMatchers.hasText(
                    LocalizationController.getInstance().getLocalizedText("transformation.added").replace("{transformation}", LocalizationController.getInstance().getLocalizedText("transformations.flipUpDown")) + System.lineSeparator()
            ));

            sleep(SLEEP_INTERVAL);
            clickOn("#bClear");
            verifyThat("#pipelineTextArea", TextInputControlMatchers.hasText(
                    ""
            ));
            sleep(SLEEP_INTERVAL);
            clickOn("#clearLogButton");
            verifyThat("#logTextArea", TextInputControlMatchers.hasText(
                    ""
            ));

            sleep(SLEEP_INTERVAL);
            clickOn("#bFlipSide");
            sleep(SLEEP_INTERVAL);
            verifyThat("#pipelineTextArea", TextInputControlMatchers.hasText(
                    LocalizationController.getInstance().getLocalizedText("transformation.flipsidetoside") + System.lineSeparator()
            ));
            verifyThat("#logTextArea", TextInputControlMatchers.hasText(
                    LocalizationController.getInstance().getLocalizedText("transformation.added").replace("{transformation}", LocalizationController.getInstance().getLocalizedText("transformations.flipSide")) + System.lineSeparator()
            ));
            sleep(SLEEP_INTERVAL);
            clickOn("#bClear");
            verifyThat("#pipelineTextArea", TextInputControlMatchers.hasText(
                    ""
            ));
            sleep(SLEEP_INTERVAL);
            clickOn("#clearLogButton");
            verifyThat("#logTextArea", TextInputControlMatchers.hasText(
                    ""
            ));

            sleep(SLEEP_INTERVAL);
            clickOn("#bRotateC");
            sleep(SLEEP_INTERVAL);
            verifyThat("#pipelineTextArea", TextInputControlMatchers.hasText(
                    LocalizationController.getInstance().getLocalizedText("transformation.rotateclockwise") + System.lineSeparator()
            ));
            verifyThat("#logTextArea", TextInputControlMatchers.hasText(
                    LocalizationController.getInstance().getLocalizedText("transformation.added").replace("{transformation}", LocalizationController.getInstance().getLocalizedText("transformations.rotateClockwise")) + System.lineSeparator()
            ));
            sleep(SLEEP_INTERVAL);
            clickOn("#bClear");
            verifyThat("#pipelineTextArea", TextInputControlMatchers.hasText(
                    ""
            ));
            clickOn("#clearLogButton");
            verifyThat("#logTextArea", TextInputControlMatchers.hasText(
                    ""
            ));

            sleep(SLEEP_INTERVAL);
            clickOn("#bRotateAC");
            sleep(SLEEP_INTERVAL);
            verifyThat("#pipelineTextArea", TextInputControlMatchers.hasText(
                    LocalizationController.getInstance().getLocalizedText("transformation.rotateanticlockwise") + System.lineSeparator()
            ));
            verifyThat("#logTextArea", TextInputControlMatchers.hasText(
                    LocalizationController.getInstance().getLocalizedText("transformation.added").replace("{transformation}", LocalizationController.getInstance().getLocalizedText("transformations.rotateAntiClockwise")) + System.lineSeparator()
            ));
            sleep(SLEEP_INTERVAL);
            clickOn("#bClear");
            verifyThat("#pipelineTextArea", TextInputControlMatchers.hasText(
                    ""
            ));
            clickOn("#clearLogButton");
            verifyThat("#logTextArea", TextInputControlMatchers.hasText(
                    ""
            ));

            sleep(SLEEP_INTERVAL);
            clickOn("#bNegative");
            sleep(SLEEP_INTERVAL);
            verifyThat("#pipelineTextArea", TextInputControlMatchers.hasText(
                    LocalizationController.getInstance().getLocalizedText("transformation.negative") + System.lineSeparator()
            ));
            verifyThat("#logTextArea", TextInputControlMatchers.hasText(
                    LocalizationController.getInstance().getLocalizedText("transformation.added").replace("{transformation}", LocalizationController.getInstance().getLocalizedText("transformations.negative")) + System.lineSeparator()
            ));
            sleep(SLEEP_INTERVAL);
            clickOn("#bClear");
            verifyThat("#pipelineTextArea", TextInputControlMatchers.hasText(
                    ""
            ));
            clickOn("#clearLogButton");
            verifyThat("#logTextArea", TextInputControlMatchers.hasText(
                    ""
            ));
        });
    }
}
