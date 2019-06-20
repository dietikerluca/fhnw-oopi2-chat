package src.createAccountClasses;

import src.ServiceLocator;
import src.abstractClasses.Model;
import src.commonClasses.ChatClient;

public class CreateAccount_Model extends Model {
    private ServiceLocator sl;

    private String username;
    private String password;

    public boolean createAccount(){
        sl = ServiceLocator.getServiceLocator();
        ChatClient chatClient = sl.getChatClient();

        return chatClient.createLogin(username, password);
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
