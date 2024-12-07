package ch.supsi.os.frontend.controller;

public class StateController {
    private static StateController instance;
    private boolean unsavedChanges = false;

    private StateController() {}

    public static StateController getInstance() {
        if (instance == null) {
            instance = new StateController();
        }
        return instance;
    }

    public boolean hasUnsavedChanges() {
        return unsavedChanges;
    }

    public void setUnsavedChanges(boolean unsavedChanges) {
        this.unsavedChanges = unsavedChanges;
    }
}
