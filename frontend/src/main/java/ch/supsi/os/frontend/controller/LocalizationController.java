package ch.supsi.os.frontend.controller;

import ch.supsi.os.frontend.model.LocalizationModel;
import ch.supsi.os.frontend.view.ControlledFxView;
import ch.supsi.os.frontend.view.LogBarViewFxml;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class LocalizationController {

    private static LocalizationController instance;
    private final LocalizationModel localizationModel;
    private final List<ControlledFxView> registeredViews = new ArrayList<>();
    private static final String LANGUAGES_RESOURCE_PATH = "/languages.properties";

    private LocalizationController() {
        localizationModel = new LocalizationModel();
    }

    public static LocalizationController getInstance() {
        if (instance == null) {
            instance = new LocalizationController();
        }
        return instance;
    }

    public void initializeLocale() {
        String userLanguage = LocalizationModel.loadPreference("language", "en");
        Locale locale = mapLanguageToLocale(userLanguage);
        localizationModel.setLocale(locale);
        notifyLocaleChanged();
    }

    public void setLocale(Locale locale) {
        localizationModel.setLocale(locale);
        notifyLocaleChanged();
    }

    public String getLocalizedText(String key) {
        ResourceBundle bundle = ResourceBundle.getBundle("translations", localizationModel.getLocale());
        return bundle.containsKey(key) ? bundle.getString(key) : "!" + key;
    }

    public List<String> getAvailableLanguages() {
        Properties languageProperties = new Properties();
        List<String> languages = new ArrayList<>();
        try (InputStream inputStream = getClass().getResourceAsStream(LANGUAGES_RESOURCE_PATH)) {
            if (inputStream != null) {
                languageProperties.load(inputStream);
                for (String key : languageProperties.stringPropertyNames()) {
                    languages.add(languageProperties.getProperty(key));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return languages.isEmpty() ? List.of("English") : languages;
    }

    public void registerView(ControlledFxView view) {
        registeredViews.add(view);
    }

    private Locale mapLanguageToLocale(String language) {
        switch (language.toLowerCase()) {
            case "en": case "english": return Locale.ENGLISH;
            case "it": case "italian": return Locale.ITALIAN;
            default: return Locale.ENGLISH;
        }
    }

    public void notifyLocaleChanged() {
        for (ControlledFxView view : registeredViews) {
            view.update();
        }

        LogBarViewFxml.getInstance().clearLog();
        LogBarViewFxml.getInstance().addLogEntry(getLocalizedText("log.language.changed"));
    }

    public String getAppProperty(String key, String defaultValue) {
        Properties properties = new Properties();
        try (InputStream input = getClass().getResourceAsStream("/application.properties")) {
            if (input != null) {
                properties.load(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(key, defaultValue);
    }

}
