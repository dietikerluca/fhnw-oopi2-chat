package src.createAccountClasses;

import src.abstractClasses.Model;

public class CreateAccount_Model extends Model {
    private String username;
    private String password;

    public boolean createAccount(){
        //TODO Create Account
        return false;
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
