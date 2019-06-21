package src.accountClasses;

import src.ServiceLocator;
import src.abstractClasses.Model;
import src.commonClasses.ChatClient;
import src.commonClasses.Translator;
import src.commonViews.ErrorPopUp;

import java.io.IOException;

public class ChangePassword_Model extends Model {

    private boolean passwordChanged = false;

    public boolean changePassword(String newPassword){
        try {
            ChatClient chatClient = ServiceLocator.getServiceLocator().getChatClient();
            passwordChanged = chatClient.changePassword(newPassword);
        } catch (IOException e) {
            Translator tr = ServiceLocator.getServiceLocator().getTranslator();

            ErrorPopUp errorPopUp = new ErrorPopUp(tr.getString("ErrorMessages.serverError") + " " + e.getMessage(),
                    tr.getString("buttons.close"));
        }

        return passwordChanged;
    }

    public boolean isPasswordChanged(){
        return passwordChanged;
    }
}
