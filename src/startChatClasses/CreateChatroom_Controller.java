package src.startChatClasses;

import src.ServiceLocator;
import src.abstractClasses.Controller;
import src.commonClasses.Translator;
import src.commonViews.ErrorPopUp;

import java.util.logging.Logger;

public class CreateChatroom_Controller extends Controller {

    public CreateChatroom_Controller(CreateChatroom_Model model, CreateChatroom_View view) {
        super(model, view);

        ServiceLocator sl = ServiceLocator.getServiceLocator();
        Translator tr = sl.getTranslator();
        Logger logger = sl.getLogger();

        view.createButton.setOnAction(event -> {
            if (view.chatroomField.getText() != ""){
                //TODO Create Chatroom
                view.stop();
            } else {
                ErrorPopUp errorPopUp = new ErrorPopUp(tr.getString("ErrorMessages.enterChatroomName"),
                        tr.getString("windows.noChatroomEntered"));
                logger.warning("No chatroom name entered.");
            }
        });
    }
}
