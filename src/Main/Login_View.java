package Main;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Login_View {

    public Stage loginStage;
    public StackPane root;
    public VBox vbox;
    public TextField usernameField;
    public PasswordField passwordField;
    public Button confirmButton;
    public Messenger model;
    public Label usernameLabel, pwLabel;
    private ServiceLocator sl = ServiceLocator.getServiceLocator();
    private Translator tr = sl.getTranslator();


    public Login_View(Messenger model){
        loginStage = new Stage();
        this.model = model;

        //Create Fields
        usernameLabel = new Label(tr.getString("LoginView.usernameLbl"));
        pwLabel = new Label(tr.getString("LoginView.passwordLbl"));
        usernameField = new TextField();
        passwordField = new PasswordField();
        confirmButton = new Button(tr.getString("LoginView.confirmBtn"));
        usernameField.setMaxWidth(150);
        passwordField.setMaxWidth(150);

        //Add fields to VBox
        vbox = new VBox();
        vbox.getChildren().addAll(usernameLabel, usernameField, pwLabel, passwordField, confirmButton);
        root = new StackPane();
        root.getChildren().add(vbox);
        root.setAlignment(vbox, Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);

        //Create Scene
        Scene scene = new Scene(root, 250, 200);
        loginStage.setAlwaysOnTop(true);
        loginStage.setMinHeight(200);
        loginStage.setMinWidth(250);
        loginStage.setTitle(tr.getString("windows.login"));
        loginStage.setScene(scene);
    }

    public void start(){
        loginStage.show();
    }

    public void stop() {
        loginStage.hide();
    }


}
