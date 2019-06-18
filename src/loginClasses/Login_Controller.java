package src.loginClasses;

import src.ChatApp;
import src.ServiceLocator;
import src.abstractClasses.Controller;
import javafx.concurrent.Worker;

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
            model.initialize();
        });

        model.initializer.stateProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED){
                if(!model.isLoginSuccessful()) { //TODO
                    view.getStage().setScene(view.getLoginSuccessful());
                } else {
                    view.getStage().setScene(view.getLoginError());
                }
            }
        }));

        view.closeTryAgain.setOnAction(event -> {
            view.getStage().setScene(view.getScene());
        });

        view.closeSuccess.setOnAction(event -> {
            main.startApp();
        });

    }

    public Login_Controller(Login_Model model, Login_View view) {
        super(model, view);

        view.confirmButton.setOnAction(event -> {
            logger.fine("Button: Confirm");
            view.getStage().setScene(view.getLoginProcess());
            model.initialize();
        });

        model.initializer.stateProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED){
                if(!model.isLoginSuccessful()) { //TODO
                    view.getStage().setScene(view.getLoginSuccessful());
                } else {
                    view.getStage().setScene(view.getLoginError());
                }
            }
        }));

        view.closeTryAgain.setOnAction(event -> {
            view.getStage().setScene(view.getScene());
        });

        view.closeSuccess.setOnAction(event -> {
            view.stop();
        });

    }
}
