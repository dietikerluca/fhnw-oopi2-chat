package src.loginClasses;

import javafx.concurrent.Task;
import src.ServiceLocator;
import src.abstractClasses.Model;
import src.commonClasses.ChatClient;

public class Login_Model extends Model {

    private Task<Void> initializer;
    private boolean loginSuccessful = false;
    private String username;
    private String password;

    public Login_Model(){
        super();
    }

    private Task<Void> createInitializer() {
        return new Task<Void>() {
            @Override
            protected Void call() {
                ChatClient chatClient = ServiceLocator.getServiceLocator().getChatClient();
                loginSuccessful = chatClient.login(username, password);

//            // Buffer
//            try {
//                TimeUnit.SECONDS.sleep(7);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

                this.updateProgress(6,  6);
                return null;
            }
        };
    }

    public void initialize() {
        initializer = createInitializer();
        new Thread(initializer).start();
    }

    public Task<Void> getInitializer() {
        return initializer;
    }

    public boolean isLoginSuccessful() {
        return loginSuccessful;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
