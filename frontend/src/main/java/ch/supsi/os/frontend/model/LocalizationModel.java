package ch.supsi.os.frontend.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationModel {

    public static Locale defaultLocale = Locale.forLanguageTag("en");
    private final ObjectProperty<Locale> locale;

    public LocalizationModel() {
        locale = new SimpleObjectProperty<>(getDefaultLocale());
        locale.addListener((observable, oldValue, newValue) -> Locale.setDefault(newValue));
    }

    public Locale getDefaultLocale() {
        return defaultLocale;
    }

    public String get(final String key, final Object... args) {
        ResourceBundle bundle = ResourceBundle.getBundle("translations", getLocale());
        return MessageFormat.format(bundle.getString(key), args);
    }

    public Locale getLocale() {
        return locale.get();
    }
}
