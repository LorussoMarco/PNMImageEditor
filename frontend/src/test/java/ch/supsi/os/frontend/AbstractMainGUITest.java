package ch.supsi.os.frontend;

import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.testfx.framework.junit5.ApplicationTest;

public abstract class AbstractMainGUITest extends ApplicationTest {
    protected static final int SLEEP_INTERVAL = 1111;
    protected static final Logger LOGGER = Logger.getAnonymousLogger();
    protected int stepNo;
    protected static Stage primaryStage;



    @BeforeAll
    public static void setupSpec(){
        if(Boolean.getBoolean("headless")){
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("java.awt.headless", "true");
        }
    }

    protected void step(final String step, final Runnable runnable){
        ++stepNo;
        LOGGER.info("STEP" + stepNo + ":" + step);
        runnable.run();
        LOGGER.info("STEP" + stepNo + ":" + "end");

    }

    @Override
    public void start(final Stage stage) throws Exception {
        primaryStage = stage;
        final MainFx mainFx = new MainFx();
        stage.toFront();
        mainFx.start(stage);

    }
}
