package src;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import src.loginClasses.Login_Controller;
import src.loginClasses.Login_Model;
import src.loginClasses.Login_View;
import src.mainClasses.Main_Controller;
import src.mainClasses.Main_Model;
import src.mainClasses.Main_View;
import src.splashScreen.Splash_Controller;
import src.splashScreen.Splash_Model;
import src.splashScreen.Splash_View;

public class ChatApp extends Application {

    private static ChatApp mainProgram;
    private Splash_View splashView;
    private Login_View loginView;
    private Main_View mainView;

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

        //Create and display Login Screen
        Login_Model login_model = new Login_Model();
        loginView = new Login_View(loginStage, login_model);
        new Login_Controller(this, login_model, loginView);

        splashView.stop();
        splashView = null;

        loginView.start();
    }


    public void startApp() {
        Stage appStage = new Stage();

        //Create and display main App screen and model
        Main_Model model = new Main_Model();
        mainView = new Main_View(appStage, model);
        new Main_Controller(model, mainView);

        // Close the splash screen, and set the reference to null, so that all
        // Splash_XXX objects can be garbage collected
        loginView.stop();
        loginView = null;

        mainView.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
