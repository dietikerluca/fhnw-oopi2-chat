package src.createAccountClasses;

import src.ServiceLocator;
import src.abstractClasses.Model;
import src.commonClasses.ChatClient;
import src.commonClasses.Translator;
import src.commonViews.ErrorPopUp;

import java.io.IOException;

public class CreateAccount_Model extends Model {
    private ServiceLocator sl;

    private String username;
    private String password;

    public boolean createAccount(){
        try {
            sl = ServiceLocator.getServiceLocator();
            ChatClient chatClient = sl.getChatClient();

            return chatClient.createLogin(username, password);
        } catch (IOException e) {
            Translator tr = ServiceLocator.getServiceLocator().getTranslator();

            ErrorPopUp errorPopUp = new ErrorPopUp(tr.getString("ErrorMessages.serverError") + " " + e.getMessage(),
                    tr.getString("buttons.close"));
            return false;
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
