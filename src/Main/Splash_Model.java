package Main;

import Abstract_Classes.Model;
import javafx.concurrent.Task;

import java.util.concurrent.TimeUnit;
import java.util.logging.*;

public class Splash_Model extends Model {

    protected ServiceLocator sl;

    public Splash_Model() {
        super();
    }

    final Task<Void> initializer = new Task<Void>() {
        @Override
        protected Void call() throws Exception {

            //Real Tasks
            sl = ServiceLocator.getServiceLocator();
            sl.setLogger(configureLogging());
            sl.setTranslator(new Translator("en"));

            //Buffer
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.updateProgress(6,  6);

            return null;
        }
    };

    public void initialize() {
        new Thread(initializer).start();
    }

    private Logger configureLogging(){

        /*Partially copied from Bradley Richards
         * --------------------------------------------------------------------*/
        Logger logger = Logger.getLogger(ServiceLocator.getServiceLocator().getAPP_NAME());
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
         * severe: std err
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
            Handler logHandler = new FileHandler("%h/Desktop/" + sl.getAPP_NAME() + "_%u" + "_%g" + ".log", 1000000, 9);
            logHandler.setLevel(Level.ALL);
            logger.addHandler(logHandler);
        } catch (Exception e) {
            throw new RuntimeException("Unable to initialize log files: " + e.toString());
        }

        return logger;
    }

}
