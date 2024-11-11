package ch.supsi.os.backend.application;

import java.util.ResourceBundle;

public interface TranslationControllerInterface {

    String translate(String key);
    ResourceBundle getTranslationBundle();
}
