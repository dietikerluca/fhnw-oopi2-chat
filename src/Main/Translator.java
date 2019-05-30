package Main;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class Translator {
    private ServiceLocator sl = ServiceLocator.getServiceLocator();
    private Logger logger = sl.getLogger();

    protected Locale currentLocale;
    private ResourceBundle resourceBundle;

    public Translator(String localeString) {
        // Can we find the language in our supported locales?
        // If not, use VM default locale
        Locale locale = new Locale("en");
        if (localeString != null) {
            Locale[] availableLocales = sl.getLocales();
            for (int i = 0; i < availableLocales.length; i++) {
                String tmpLang = availableLocales[i].getLanguage();
                if (localeString.substring(0, tmpLang.length()).equals(tmpLang)) {
                    locale = availableLocales[i];
                    break;
                }
            }
        }

        // Load the resource strings
        resourceBundle = ResourceBundle.getBundle("Messenger", locale);
        Locale.setDefault(locale); // Change VM default (for dialogs, etc.)
        currentLocale = locale;

        logger.info("Loaded resources for " + locale.getLanguage());
    }

    public Locale getCurrentLocale() {
        return currentLocale;
    }


    public String getString(String key) {
        try {
            return resourceBundle.getString(key);
        } catch (MissingResourceException e) {
            logger.warning("Missing string: " + key);
            return "--";
        }
    }
}
