package Main;

import Abstract_Classes.Model;
import javafx.concurrent.Task;

import java.util.concurrent.TimeUnit;

public class Login_Model extends Model {

    private boolean loginSuccessful = false;

    public Login_Model(){
        super();
    }

    final Task<Void> initializer = new Task<Void>() {
        @Override
        protected Void call() throws Exception {


            //Buffer
            try {
                TimeUnit.SECONDS.sleep(7);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.updateProgress(6,  6);

            return null;
        }
    };

    public void initialize() {
        new Thread(initializer).start();
    }

    public boolean isLoginSuccessful() {
        return loginSuccessful;
    }

}
