package ch.supsi.os.backend.dataAccess;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public interface TranslationDataAccessInterface {
    List<String> getSupportedLanguageTags();
    ResourceBundle getTranslations(Locale locale);
}
