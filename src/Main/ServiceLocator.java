package Main;

import java.util.Locale;
import java.util.logging.*;

public class ServiceLocator {
    private static ServiceLocator serviceLocator;

    final private Class<?> APP_CLASS = Main.class;
    final private String APP_NAME = "Messenger";

    // Supported locales (for translations)
    final private Locale[] locales = new Locale[] { new Locale("en"), new Locale("de") };

    //Default Values User has set
    private String ipAddressPreset = "192.168.1.0";
    private int port = 82;

    private Logger logger;
    private Configuration configuration;
    private Translator translator;


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

    private ServiceLocator() {
        /*Partially copied from Bradley Richards
        * --------------------------------------------------------------------*/
        logger = Logger.getLogger(APP_NAME);
        logger.setLevel(Level.ALL); // set the default logger level

        // By default there is one handler: the console
        Handler[] defaultHandlers = Logger.getLogger("").getHandlers();
        if (defaultHandlers.length == 1) {
            // Configure the console handler
            defaultHandlers[0].setLevel(Level.OFF); //Deactivated, because defined a new one below
        } else {
            throw new RuntimeException("More than one default handler found");
        }

        /*Handler for differentiation between sever and not severe logs
        * not severe: std out
        * sever: std err
        * ------------------------------------------------------------------*/
        Handler consoleHandler = new Handler(){
            @Override
            public void publish(LogRecord record)
            {
                if (getFormatter() == null)
                {
                    setFormatter(new SimpleFormatter());
                }

                try {
                    String message = getFormatter().format(record);
                    if (record.getLevel().intValue() >= Level.WARNING.intValue())
                    {
                        System.err.write(message.getBytes());
                    }
                    else
                    {
                        System.out.write(message.getBytes());
                    }
                } catch (Exception exception) {
                    reportError(null, exception, ErrorManager.FORMAT_FAILURE);
                }

            }

            @Override
            public void close() throws SecurityException {}
            @Override
            public void flush(){}
        };
        logger.addHandler(consoleHandler);

        // Add a file handler: put rotating files in the tmp directory
        try {
            // %u is for file-conflicts; this happens if multiple instances
            // of the program are running. %g is the rotating logfile number
            Handler logHandler = new FileHandler("%h/Desktop/" + APP_NAME + "_%u" + "_%g" + ".log", 1000000, 9);
            logHandler.setLevel(Level.ALL);
            logger.addHandler(logHandler);
        } catch (Exception e) {
            throw new RuntimeException("Unable to initialize log files: " + e.toString());
        }
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
        logger.config("Translator Set to "+translator.getCurrentLocale().getLanguage());
    }


    public String getIpAddressPreset() {
        return ipAddressPreset;
    }

    public int getPort() {
        return port;
    }
}

