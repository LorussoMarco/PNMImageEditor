package ch.supsi.os.backend.business;

import ch.supsi.tictactoe.backend.dataAccess.TranslationDataAccess;
import ch.supsi.tictactoe.backend.dataAccess.TranslationDataAccessInterface;

import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class Translation implements TranslationInterface {
    private static Translation myself;

    private final TranslationDataAccessInterface dataAccess = TranslationDataAccess.getInstance();
    private Properties translations;
    private ResourceBundle translationBundle;

    private Translation() {
    }


    public static Translation getInstance() {
        if (myself== null) {
            myself = new Translation();
        }

        return myself;
    }

    @Override
    public void changeLanguage(String languageTag) {

        translations = new Properties();
        translationBundle = dataAccess.getTranslations(Locale.forLanguageTag(languageTag));

        for (String key : translationBundle.keySet()) {
            translations.put(key, translationBundle.getString(key));
        }
    }

    @Override
    public String translate(String key) {
        return translations.getProperty(key);
    }

    public ResourceBundle getTranslationBundle() {
        return translationBundle;
    }
}
