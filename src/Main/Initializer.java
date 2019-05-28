package Main;

import javafx.stage.Stage;

public class Initializer {

    Stage mainStage;
    Messenger model;
    View view;
    Controller controller;
    Boolean login;

    ServiceLocator serviceLocator;


    public Initializer(){
        mainStage = new Stage();

        // Initialize the GUI, Model, Controller
        model = new Messenger();
        view = new View(mainStage, model);
        controller = new Controller(model, view);

        serviceLocator = ServiceLocator.getServiceLocator();

    }

    public void ready(Boolean login){
        if (login == true){
            Login_View loginView = new Login_View(model);
            loginView.start();
        }
        view.start();
    }


}
