package src.loginClasses;

import javafx.concurrent.Worker;
import javafx.scene.input.KeyCode;
import src.ChatApp;
import src.ServiceLocator;
import src.abstractClasses.Controller;

import java.util.logging.Logger;

public class Login_Controller extends Controller {

    private ServiceLocator sl = ServiceLocator.getServiceLocator();
    private Logger logger = sl.getLogger();
    protected Login_View view;

    public Login_Controller(ChatApp main, Login_Model model, Login_View view) {
        super(model, view);

        view.confirmButton.setOnAction(event -> {
            logger.fine("Button: Confirm");
            view.getStage().setScene(view.getLoginProcess());

            model.setUsername(view.usernameField.getText());
            model.setPassword(view.passwordField.getText());

            model.initialize();
            model.getInitializer().stateProperty().addListener(((observable, oldValue, newValue) -> {
                if (newValue == Worker.State.SUCCEEDED){
                    if (model.isLoginSuccessful()) {
                        view.getStage().setScene(view.getLoginSuccessful());
                        logger.info("Login successful");
                    } else {
                        view.getStage().setScene(view.getLoginError());
                        logger.warning("Login not successful");
                    }
                }
            }));
        });

        view.closeTryAgain.setOnAction(event -> {
            view.getStage().setScene(view.getScene());
        });

        view.closeSuccess.setOnAction(event -> {
            main.startApp(model.getUsername());
        });
    }

    public Login_Controller(Login_Model model, Login_View view) {
        super(model, view);

        view.confirmButton.setOnAction(event -> {
            logger.fine("Button: Confirm");
            view.getStage().setScene(view.getLoginProcess());

            model.setUsername(view.usernameField.getText());
            model.setPassword(view.passwordField.getText());

            model.initialize();
            model.getInitializer().stateProperty().addListener(((observable, oldValue, newValue) -> {
                if (newValue == Worker.State.SUCCEEDED){
                    if(model.isLoginSuccessful()) {
                        view.getStage().setScene(view.getLoginSuccessful());
                        logger.fine("Login successful");
                    } else {
                        view.getStage().setScene(view.getLoginError());
                        logger.warning("Login not successful");
                    }
                }
            }));
        });

        view.passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER){
                logger.fine("Key: Enter");
                view.getStage().setScene(view.getLoginProcess());

                model.setUsername(view.usernameField.getText());
                model.setPassword(view.passwordField.getText());

                model.initialize();
                model.getInitializer().stateProperty().addListener(((observable, oldValue, newValue) -> {
                    if (newValue == Worker.State.SUCCEEDED){
                        if(model.isLoginSuccessful()) {
                            view.getStage().setScene(view.getLoginSuccessful());
                            logger.fine("Login successful");
                        } else {
                            view.getStage().setScene(view.getLoginError());
                            logger.warning("Login not successful");
                        }
                    }
                }));
            }
        });

        view.closeTryAgain.setOnAction(event -> {
            view.getStage().setScene(view.getScene());
            logger.fine("Button: Try Again (Close)");
        });

        view.closeSuccess.setOnAction(event -> {
            view.stop();
            logger.fine("Button: Success (Close)");
        });

    }
}
