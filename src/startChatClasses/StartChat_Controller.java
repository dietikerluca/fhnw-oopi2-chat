package src.startChatClasses;

import src.ServiceLocator;
import src.abstractClasses.Controller;
import src.commonClasses.Translator;
import src.commonViews.ErrorPopUp;
import src.mainClasses.Main_Model;

import java.util.logging.Logger;

public class StartChat_Controller extends Controller {


    public StartChat_Controller(Main_Model model, StartChat_View view) {
        super(model, view);

        view.startChat.setOnAction(event -> {
            ServiceLocator sl = ServiceLocator.getServiceLocator();
            Translator tr = sl.getTranslator();
            Logger logger = sl.getLogger();

            if (view.sendTo.getText().isEmpty()){
                ErrorPopUp errorPopUp = new ErrorPopUp(tr.getString("ErrorMessages.noUsernameEntered"),
                        tr.getString("windows.missingUsername"));
                logger.warning("No Username entered.");
            } else {
                model.createPrivateChat(view.sendTo.getText());
            }
        });
    }
}
