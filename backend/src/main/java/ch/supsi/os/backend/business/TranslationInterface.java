package ch.supsi.os.backend.business;

import java.util.ResourceBundle;

public interface TranslationInterface {
    void changeLanguage(String languageTag);
    String translate(String key);
    ResourceBundle getTranslationBundle();
}
