package src.splashScreen;

import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.stage.Stage;
import src.ChatApp;
import src.ServiceLocator;
import src.abstractClasses.Controller;
import src.commonClasses.Translator;
import src.commonViews.ErrorPopUp;
import src.createAccountClasses.CreateAccount_Controller;
import src.createAccountClasses.CreateAccount_Model;
import src.createAccountClasses.CreateAccount_View;
import src.serverChangeClasses.ServerChange_Controller;
import src.serverChangeClasses.ServerChange_Model;
import src.serverChangeClasses.ServerChange_View;

public class Splash_Controller extends Controller {
    public Splash_Controller(ChatApp main, Splash_Model model, Splash_View view) {
        super(model, view);

        model.initializer.stateProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue == Worker.State.SUCCEEDED) view.getStage().setScene(view.createLoginOption_GUI());
                if (newValue == Worker.State.FAILED) {
                    Platform.runLater(() -> {
                        System.out.println("failed");
                        Translator tr = ServiceLocator.getServiceLocator().getTranslator();
                        ErrorPopUp errorPopUp = new ErrorPopUp(tr.getString("ErrorMessages.serverError") + " No Connection",
                                tr.getString("buttons.close"));

                        errorPopUp.closeBtn.setOnAction(event -> {
                            Stage serverChangeStage = new Stage();
                            ServerChange_Model serverChange_model = new ServerChange_Model();
                            ServerChange_View serverChange_view = new ServerChange_View(serverChangeStage, serverChange_model);
                            ServerChange_Controller serverChange_controller = new ServerChange_Controller(
                                    serverChange_model, serverChange_view, main);
                            serverChange_view.start();
                            errorPopUp.close();
                            view.stop();
                        });
                    });
                }
            });

        view.login.setOnAction(event -> main.startLogin());
        view.createAccount.setOnAction(event -> main.startAccountCreation());

        view.changeServer.setOnAction(event -> {
            Stage serverChangeStage = new Stage();
            ServerChange_Model serverChange_model = new ServerChange_Model();
            ServerChange_View serverChange_view = new ServerChange_View(serverChangeStage, serverChange_model);
            ServerChange_Controller serverChange_controller = new ServerChange_Controller(
                    serverChange_model, serverChange_view, main);
            serverChange_view.start();
        });
    }
}
