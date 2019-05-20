package Main;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    Messenger model;
    View view;
    Controller controller;
    ServiceLocator serviceLocator;


    public void start(Stage primaryStage) {
        Stage mainStage = new Stage();
        // Initialize the GUI
        model = new Messenger();
        view = new View(mainStage, model);
        controller = new Controller(model, view);

        serviceLocator = ServiceLocator.getServiceLocator();

        // Display the GUI after all initialization is complete
        view.start();

    }

    public void startApp(){

        Stage mainStage = new Stage();
        // Initialize the GUI
        model = new Messenger();
        view = new View(mainStage, model);
        controller = new Controller(model, view);

        // Display the GUI after all initialization is complete
        view.start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
