package src.createAccountClasses;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import src.ServiceLocator;
import src.abstractClasses.Model;
import src.abstractClasses.View;
import src.commonClasses.Translator;

import java.util.logging.Logger;

public class CreateAccount_View extends View {

    ServiceLocator sl;
    Translator tr;
    Logger logger;

    private Label usernameLabel, pwLabel, header;
    public TextField usernameField;
    public PasswordField passwordField;
    public Button confirmButton;


    public CreateAccount_View(Stage stage, Model model) {
        super(stage, model);

        stage.initStyle(StageStyle.UNDECORATED);
        stage.setAlwaysOnTop(true);
        stage.setTitle(tr.getString("windows.ceateAccount"));

        stage.setScene(create_GUI());
    }

    @Override
    protected Scene create_GUI() {
        sl = ServiceLocator.getServiceLocator();
        tr = sl.getTranslator();
        logger = sl.getLogger();

        StackPane root = new StackPane();
        VBox vbox = new VBox();

        // Create Scene
        scene = new Scene(root, 250, 400);

        // Create Fields
        header = new Label(tr.getString("header.createAccount"));
        header.getStyleClass().add("header");

        usernameLabel = new Label(tr.getString("CreateAccountView.usernameLbl"));
        usernameLabel.getStyleClass().add("defaultLabel");

        pwLabel = new Label(tr.getString("CreateAccountView.passwordLbl"));
        pwLabel.getStyleClass().add("defaultLabel");

        usernameField = new TextField();
        usernameField.setMaxWidth(150);

        passwordField = new PasswordField();
        passwordField.setMaxWidth(150);

        confirmButton = new Button(tr.getString("LoginView.confirmBtn"));
        confirmButton.getStyleClass().add("confirm");

        // ButtonBox for alignment of Buttons
        HBox buttonBox = new HBox();
        buttonBox.getChildren().add(confirmButton);
        buttonBox.setAlignment(Pos.CENTER);

        // Add fields to VBox
        vbox.getChildren().addAll(header, usernameLabel, usernameField, pwLabel, passwordField, buttonBox);
        root.getChildren().add(vbox);
        StackPane.setAlignment(vbox, Pos.CENTER);

        vbox.setSpacing(20);
        vbox.setAlignment(Pos.CENTER_LEFT);

        String stylesheet = sl.getClass().getResource("stylesheet.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);

        return scene;
    }
}
