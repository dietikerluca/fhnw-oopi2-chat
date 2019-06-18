package src.splashScreen;

import javafx.concurrent.Worker;
import javafx.stage.Stage;
import src.ChatApp;
import src.ServiceLocator;
import src.abstractClasses.Controller;
import src.createAccountClasses.CreateAccount_Controller;
import src.createAccountClasses.CreateAccount_Model;
import src.createAccountClasses.CreateAccount_View;

public class Splash_Controller extends Controller {

    ServiceLocator sl = ServiceLocator.getServiceLocator();

    public Splash_Controller(ChatApp main, Splash_Model model, Splash_View view) {
        super(model, view);

        model.initializer.stateProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue == Worker.State.SUCCEEDED) view.getStage().setScene(view.createLoginOption_GUI());
            });

        view.login.setOnAction(event -> {
            main.startLogin();
        });

        view.createAccount.setOnAction(event -> {
            CreateAccount_Model createAccount_model = new CreateAccount_Model();
            CreateAccount_View createAccount_view = new CreateAccount_View(new Stage(), createAccount_model);
            CreateAccount_Controller createAccount_controller = new CreateAccount_Controller(createAccount_model, createAccount_view);

            createAccount_view.start();
        });
    }
}
