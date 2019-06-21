package src;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import src.createAccountClasses.CreateAccount_Controller;
import src.createAccountClasses.CreateAccount_Model;
import src.createAccountClasses.CreateAccount_View;
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
    private CreateAccount_View createAccount_view;

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

    public void startAccountCreation() {
        Stage createAccountStage = new Stage();

        //Create and display Login Screen
        CreateAccount_Model createAccount_model = new CreateAccount_Model();
        createAccount_view = new CreateAccount_View(createAccountStage, createAccount_model);
        new CreateAccount_Controller(this, createAccount_model, createAccount_view);

        splashView.stop();
        splashView = null;

        createAccount_view.start();
    }


    public void startApp(String username) {
        Stage appStage = new Stage();

        // Create and display main App screen and model
        Main_Model model = new Main_Model(username);
        mainView = new Main_View(appStage, model);
        new Main_Controller(model, mainView);

        // Close all other windows
        if (loginView != null) {
            loginView.stop();
            loginView = null;
        }
        if (createAccount_view != null) {
            createAccount_view.stop();
            createAccount_view = null;
        }

        mainView.start();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
