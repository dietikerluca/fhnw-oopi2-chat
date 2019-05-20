package Main;

import java.util.Locale;
import java.util.logging.Logger;

public class ServiceLocator {
    private static ServiceLocator serviceLocator;

    final private Class<?> APP_CLASS = Main.class;
    final private String APP_NAME = "Messenger";

    // Supported locales (for translations)
    final private Locale[] locales = new Locale[] { new Locale("en"), new Locale("de") };

    //Default Values User has set
    private boolean ipPreset = false;
    private boolean portPreset = false;
    private int ipAddressPreset, port;

    private Logger logger;
    private Configuration configuration;
    private Translator translator;


    public static ServiceLocator getServiceLocator() {
        if (serviceLocator == null)
            serviceLocator = new ServiceLocator();
        return serviceLocator;
    }

    private ServiceLocator() {
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
        } return translator;
    }

    public void setTranslator(Translator translator) {
        this.translator = translator;
    }

    public void setIpPreset(int ipAddress){
        ipAddressPreset = ipAddress;
        ipPreset = true;
    }

    public void setPortPreset(int port){
        port = port;
        portPreset = true;
    }

    public boolean isIpPreset() {
        return ipPreset;
    }

    public boolean isPortPreset() {
        return portPreset;
    }

    public int getIpAddressPreset() {
        return ipAddressPreset;
    }

    public int getPort() {
        return port;
    }
}

