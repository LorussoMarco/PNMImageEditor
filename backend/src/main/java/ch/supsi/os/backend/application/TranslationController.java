package ch.supsi.os.backend.application;


import ch.supsi.os.backend.business.Translation;
import ch.supsi.os.backend.business.TranslationInterface;

import java.util.ResourceBundle;

public class TranslationController implements TranslationControllerInterface{
    private static TranslationController myself;

    private final TranslationInterface translations = Translation.getInstance();

    protected TranslationController() {
        PreferencesInterface preferences = Preferences.getInstance();
        String currentLanguage = preferences.getCurrentLanguage();
        translations.changeLanguage(currentLanguage);
    }

    public static TranslationController getInstance() {
        if (myself == null) {
            myself = new TranslationController();
        }

        return myself;
    }

    @Override
    public String translate(String key) {
        return translations.translate(key);
    }

    @Override
    public ResourceBundle getTranslationBundle() {
        return translations.getTranslationBundle();
    }
}

