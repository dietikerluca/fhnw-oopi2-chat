package src.accountClasses;

import src.ServiceLocator;
import src.abstractClasses.Model;
import src.commonClasses.ChatClient;

public class ChangePassword_Model extends Model {

    private boolean passwordChanged = false;

    public boolean changePassword(String newPassword){

        ChatClient chatClient = ServiceLocator.getServiceLocator().getChatClient();
        passwordChanged = chatClient.changePassword(newPassword);

        return passwordChanged;
    }

    public boolean isPasswordChanged(){
        return passwordChanged;
    }
}
