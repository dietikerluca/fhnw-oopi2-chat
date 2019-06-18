package src;

import src.commonClasses.ChatClient;
import src.commonClasses.Configuration;
import src.commonClasses.Translator;

import java.util.Locale;
import java.util.logging.Logger;

public class ServiceLocator {
    private static ServiceLocator serviceLocator;

    final private Class<?> APP_CLASS = ChatApp.class;
    final private String APP_NAME = "ChatApp";

    // Supported locales (for translations)
    final private Locale[] locales = new Locale[] { new Locale("en"), new Locale("de") };

    // Default Values User has set
    private String ipAddressPreset = "147.86.8.31";
    private int port = 50001;

    private Logger logger;
    private Configuration configuration;
    private Translator translator;
    private ChatClient chatClient;

    private ServiceLocator() {

    }

    public static ServiceLocator getServiceLocator() {
        if (serviceLocator == null)
            serviceLocator = new ServiceLocator();
        return serviceLocator;
    }

    public void setIpAddressPreset(String ipAddressPreset) {
        this.ipAddressPreset = ipAddressPreset;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Class<?> getAPP_CLASS() {
        return APP_CLASS;
    }

    public String getAPP_NAME() {
        return APP_NAME;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public Locale[] getLocales() {
        return locales;
    }

    //Get Translator
    public Translator getTranslator() {
        if (translator == null){
            translator = new Translator("English");
        }
        return translator;
    }

    public void setTranslator(Translator translator) {
        this.translator = translator;
        logger.config("Translator Set to "+translator.getCurrentLocale().getLanguage());
    }

    public ChatClient getChatClient() {
        if (chatClient == null) {
            chatClient = new ChatClient(ipAddressPreset, port);
        }
        return chatClient;
    }

    public String getIpAddressPreset() {
        return ipAddressPreset;
    }

    public int getPort() {
        return port;
    }
}

