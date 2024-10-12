package ch.supsi.os.frontend.view;

import ch.supsi.os.frontend.controller.EventHandler;

public interface ControlledView extends DataView {
    void initialize(EventHandler eventHandler);
}
