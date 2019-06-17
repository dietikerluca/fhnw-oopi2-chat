package Main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class Main extends Application {

    private static Main mainProgram;
    private Splash_View splashView;
    private Login_View login_view;
    private Main_View view;
    private ServiceLocator serviceLocator; // resources, after initialization

    private Main_Model model;

    @Override
    public void init() {
        if (mainProgram == null) {
            mainProgram = this;
        } else {
            Platform.exit();
        }
    }

    public void start(Stage primaryStage) {

        // Create and display the splash screen and model
        Splash_Model splashModel = new Splash_Model();
        splashView = new Splash_View(primaryStage, splashModel);
        new Splash_Controller(this, splashModel, splashView);

        // Display the splash screen and begin the initialization
        splashModel.initialize();
        splashView.start();
    }

    public void startLogin() {
        Stage loginStage = new Stage();

        // Resources are now initialized
        serviceLocator = ServiceLocator.getServiceLocator();

        //Create and display Login Screen
        Login_Model login_model = new Login_Model();
        login_view = new Login_View(loginStage, login_model);
        new Login_Controller(this, login_model, login_view);

        splashView.stop();
        splashView = null;

        login_view.start();
    }


    public void startApp() {
        Stage appStage = new Stage();

        //Create and display main App screen and model
        Main_Model model = new Main_Model();
        view = new Main_View(appStage, model);
        new Main_Controller(model, view);

        // Close the splash screen, and set the reference to null, so that all
        // Splash_XXX objects can be garbage collected
        login_view.stop();
        login_view = null;

        view.start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
