package src.loginClasses;

import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;
import javafx.util.Duration;
import src.ServiceLocator;
import src.abstractClasses.View;
import src.commonClasses.Translator;
import src.commonViews.ImageLoader;

import java.util.logging.Logger;

public class Login_View extends View {

    private ServiceLocator sl;
    private Translator tr;
    private Logger logger;

    TextField usernameField;
    PasswordField passwordField;
    Button confirmButton, closeSuccess, closeTryAgain;

    private VBox vbox;
    private Label usernameLabel, pwLabel;

    private Scene loginProcess, loginSuccessful, loginError, scene;

    private ImageView headerImageView, serverImageView;

    public Login_View(Stage stage, Login_Model model) {
        super(stage, model);

        stage.setAlwaysOnTop(true);
        stage.setAlwaysOnTop(true);
        stage.setMinHeight(400);
        stage.setMinWidth(250);
        stage.setTitle(tr.getString("windows.login"));

        logger.info("Login View initialized");
    }

    public Scene create_GUI() {
        sl = ServiceLocator.getServiceLocator();
        tr = sl.getTranslator();
        logger = sl.getLogger();

        StackPane root = new StackPane();
        vbox = new VBox();

        // Create Scene
        scene = new Scene(root, 250, 400);

        // Load icons
        try {
            headerImageView = ImageLoader.loadImageView("/src/assets/img/login_robo.png", 70,70, "headerIcon");
            serverImageView = ImageLoader.loadImageView("/src/assets/img/server.png", 70,70, "headerIcon");

        } catch (Exception e){
            logger.warning("image could not be loaded. \n Stack Trace: " + e);
        }
        vbox.getChildren().add(headerImageView);

        // Create Fields
        usernameLabel = new Label(tr.getString("LoginView.usernameLbl"));
        usernameLabel.getStyleClass().add("defaultLabel");

        pwLabel = new Label(tr.getString("LoginView.passwordLbl"));
        pwLabel.getStyleClass().add("defaultLabel");

        usernameField = new TextField();
        usernameField.setMaxWidth(150);

        passwordField = new PasswordField();
        passwordField.setMaxWidth(150);

        confirmButton = new Button(tr.getString("LoginView.confirmBtn"));
        confirmButton.getStyleClass().add("confirm");

        // Add fields to VBox
        vbox.getChildren().addAll(usernameLabel, usernameField, pwLabel, passwordField, confirmButton);
        root.getChildren().add(vbox);
        StackPane.setAlignment(vbox, Pos.CENTER);

        vbox.setSpacing(20);
        vbox.setAlignment(Pos.CENTER);

        String stylesheet = sl.getClass().getResource("stylesheet.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);

        // Create other Views
        createLoginSequence_GUI();
        createLoginSuccessful_GUI();
        createLoginError_GUI();

        return scene;
    }

    private void createLoginSequence_GUI() {
        VBox vboxProcess = new VBox();

        // Create scene
        vboxProcess.getChildren().addAll(headerImageView, createLoadAnimation(), serverImageView);
        vboxProcess.setAlignment(Pos.CENTER);
        vboxProcess.setSpacing(20);

        loginProcess = new Scene(vboxProcess, 250, 400);
    }

    private void createLoginSuccessful_GUI() {
        VBox vboxSuccess = new VBox();
        loginSuccessful = new Scene(vboxSuccess, 250, 400);

        // Load success icon
        try {
            vboxSuccess.getChildren().add(ImageLoader.loadImageView("/src/assets/img/success_robo.png", 70,70, "headerIcon"));
        } catch (Exception e){
            logger.warning("image could not be loaded. \n Stack Trace: " + e);
        }

        Label successLabel = new Label(tr.getString("labels.loginSuccessful"));
        closeSuccess = new Button(tr.getString("buttons.close"));
        closeSuccess.getStyleClass().add("secondary");
        successLabel.getStyleClass().add("defaultLabel");

        vboxSuccess.getChildren().addAll(successLabel, closeSuccess);
        vboxSuccess.setAlignment(Pos.CENTER);
        vboxSuccess.setSpacing(20);

        String stylesheet = sl.getClass().getResource("stylesheet.css").toExternalForm();
        loginSuccessful.getStylesheets().add(stylesheet);

    }

    private void createLoginError_GUI() {
        VBox vboxError = new VBox();
        loginError = new Scene(vboxError, 250, 400);

        // load Error Icon
        try {
            vboxError.getChildren().add(ImageLoader.loadImageView("/src/assets/img/surprized_question_robo.png", 70,70, "headerIcon"));
        } catch (Exception e){
            logger.warning("image could not be loaded. \n Stack Trace: " + e);
        }

        Label errorLabel = new Label(tr.getString("labels.loginError"));
        closeTryAgain = new Button(tr.getString("buttons.tryAgain"));
        closeTryAgain.getStyleClass().add("secondary");
        errorLabel.getStyleClass().add("defaultLabel");

        vboxError.getChildren().addAll(errorLabel, closeTryAgain);
        vboxError.setAlignment(Pos.CENTER);
        vboxError.setSpacing(20);

        String stylesheet = sl.getClass().getResource("stylesheet.css").toExternalForm();
        loginError.getStylesheets().add(stylesheet);
    }

    private Group createLoadAnimation() {
        final int SPEED = 300;

        // Initialize Circles
        Ellipse dot1 = new Ellipse(50, 50,7, 7);
        Ellipse dot2 = new Ellipse(50, 80,7, 7);
        Ellipse dot3 = new Ellipse(50, 110,7, 7);
        Ellipse current = new Ellipse(50, 50,7, 7);
        dot1.setFill(Color.BLACK);
        dot2.setFill(Color.LIGHTGREY);
        dot3.setFill(Color.LIGHTGREY);

        Group group = new Group();
        group.getChildren().addAll(dot1, dot2, dot3);

        // Create Transitions
        FillTransition unfillDot1 = new FillTransition(Duration.millis(SPEED), dot1, Color.BLACK, Color.LIGHTGREY);
        FillTransition fillDot2 = new FillTransition(Duration.millis(SPEED), dot2, Color.LIGHTGREY, Color.BLACK);
        ParallelTransition par1 = new ParallelTransition(unfillDot1, fillDot2);

        FillTransition unfillDot2 = new FillTransition(Duration.millis(SPEED), dot2, Color.BLACK, Color.LIGHTGREY);
        FillTransition fillDot3 = new FillTransition(Duration.millis(SPEED), dot3, Color.LIGHTGREY, Color.BLACK);
        ParallelTransition par2 = new ParallelTransition(unfillDot2, fillDot3);

        SequentialTransition seq = new SequentialTransition(par1, par2);
        seq.setCycleCount(Animation.INDEFINITE);
        seq.setAutoReverse(true);
        seq.play();

        return group;
    }

    public Scene getLoginProcess(){
        return loginProcess;
    }

    public Scene getLoginSuccessful(){
        return loginSuccessful;
    }

    public Scene getLoginError(){
        return loginError;
    }

    public Scene getScene(){
        return scene;
    }

}
