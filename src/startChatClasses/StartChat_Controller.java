package src.startChatClasses;

import src.abstractClasses.Controller;

public class StartChat_Controller extends Controller {


    public StartChat_Controller(StartChat_Model model, StartChat_View view) {
        super(model, view);

        view.startChat.setOnAction(event -> {

            //TODO Send message to User

            //TODO Error message if user doesent exists
        });


    }

}
