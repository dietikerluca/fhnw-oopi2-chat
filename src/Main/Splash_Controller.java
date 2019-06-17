package Main;

import Abstract_Classes.Controller;
import javafx.concurrent.Worker;

public class Splash_Controller extends Controller {

    ServiceLocator sl = ServiceLocator.getServiceLocator();

    public Splash_Controller(Main main, Splash_Model model, Splash_View view) {
        super(model, view);

        model.initializer.stateProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue == Worker.State.SUCCEEDED)
                        view.getStage().setScene(view.createLoginOption_GUI());
                });

        view.login.setOnAction(event -> {
            main.startLogin();
        });

        view.createAccount.setOnAction(event -> {
            main.startApp();
        });
    }


}
