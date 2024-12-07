package ch.supsi.os.frontend.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StateControllerTest {

    @Test
    void testSingletonInstance() {
        StateController instance1 = StateController.getInstance();
        StateController instance2 = StateController.getInstance();

        assertNotNull(instance1, "Instance should not be null.");
        assertSame(instance1, instance2, "Both instances should be the same (singleton).");
    }

    @Test
    void testInitialUnsavedChangesState() {
        StateController stateController = StateController.getInstance();
        assertFalse(stateController.hasUnsavedChanges(), "Initial state of unsaved changes should be false.");
    }

    @Test
    void testSetUnsavedChanges() {
        StateController stateController = StateController.getInstance();
        stateController.setUnsavedChanges(true);

        assertTrue(stateController.hasUnsavedChanges(), "State of unsaved changes should be true after setting it to true.");

        stateController.setUnsavedChanges(false);
        assertFalse(stateController.hasUnsavedChanges(), "State of unsaved changes should be false after setting it to false.");
    }
}
