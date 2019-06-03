package Main;

import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

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
    private Logger logger = sl.getLogger();
    private Image errorRobo, server;
    private ImageView headerImage, serverImage;
    private String url;


    public Login_View(Messenger model){
        loginStage = new Stage();
        this.model = model;
        vbox = new VBox();

        /*Load Image
         * ----------------------------------------*/
        try {
            url = System.getProperty("user.dir");
            url += "/src/Image/login_robo.png";
            File image = new File(url);
            url = image.toURI().toURL().toString();
            errorRobo = new Image(url);
            headerImage = new ImageView(errorRobo);
            headerImage.setFitHeight(70);
            headerImage.setFitWidth(70);
            headerImage.getStyleClass().add("headerIcon");
            vbox.getChildren().add(headerImage);
            logger.fine("Image loaded.");
        } catch (Exception e){
            e.printStackTrace();
            logger.warning("Image could not be loaded. \n Stack Trace: "+e.getStackTrace().toString());
        }

        //Create Fields
        usernameLabel = new Label(tr.getString("LoginView.usernameLbl"));
        usernameLabel.getStyleClass().add("defaultLabel");
        pwLabel = new Label(tr.getString("LoginView.passwordLbl"));
        pwLabel.getStyleClass().add("defaultLabel");
        usernameField = new TextField();
        passwordField = new PasswordField();
        confirmButton = new Button(tr.getString("LoginView.confirmBtn"));
        confirmButton.getStyleClass().add("confirm");
        usernameField.setMaxWidth(150);
        passwordField.setMaxWidth(150);

        //Add fields to VBox
        vbox.getChildren().addAll(usernameLabel, usernameField, pwLabel, passwordField, confirmButton);
        root = new StackPane();
        root.getChildren().add(vbox);
        root.setAlignment(vbox, Pos.CENTER);
        vbox.setSpacing(20);
        vbox.setAlignment(Pos.CENTER);

        //Create Scene
        Scene scene = new Scene(root, 250, 400);
        loginStage.setAlwaysOnTop(true);
        loginStage.setMinHeight(400);
        loginStage.setMinWidth(250);
        loginStage.setTitle(tr.getString("windows.login"));
        loginStage.setScene(scene);

        String stylesheet = getClass().getResource("stylesheet.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);

        confirmButton.setOnAction(event -> {
            logger.fine("Button: Confirm");
            loginSequence(); //TODO
        });
    }

    public void start(){
        loginStage.show();
    }

    public void stop() {
        loginStage.hide();
    }

    public void loginSequence(){
        VBox vboxProcess = new VBox();
        Group group = new Group();

        /*Load Server Image
         * ----------------------------------------*/
        try {
            url = System.getProperty("user.dir");
            url += "/src/Image/server.png";
            File image = new File(url);
            url = image.toURI().toURL().toString();
            server = new Image(url);
            serverImage = new ImageView(server);
            serverImage.setFitHeight(70);
            serverImage.setFitWidth(70);
            serverImage.getStyleClass().add("headerIcon");
            vbox.getChildren().add(serverImage);
        } catch (Exception e){
            logger.warning("Image could not be loaded. \n Stack Trace: "+e.getStackTrace().toString());
        }

        /*Create Animation Dots
        * ----------------------------------------------------------------------*/
        Ellipse dot1 = new Ellipse(50, 50,7, 7);
        Ellipse dot2 = new Ellipse(50, 80,7, 7);
        Ellipse dot3 = new Ellipse(50, 110,7, 7);
        Ellipse current = new Ellipse(50, 50,7, 7);
        dot1.setFill(Color.BLACK);
        dot2.setFill(Color.LIGHTGREY);
        dot3.setFill(Color.LIGHTGREY);
        group.getChildren().addAll(dot1, dot2, dot3);


        /*Animation Setup
        * -------------------------------------------------------------------------------------------------------*/
        FillTransition unfillDot1 = new FillTransition(Duration.millis(500), dot1, Color.BLACK, Color.LIGHTGREY);
        FillTransition fillDot2 = new FillTransition(Duration.millis(500), dot2, Color.LIGHTGREY, Color.BLACK);
        ParallelTransition par1 = new ParallelTransition(unfillDot1, fillDot2);

        FillTransition unfillDot2 = new FillTransition(Duration.millis(500), dot2, Color.BLACK, Color.LIGHTGREY);
        FillTransition fillDot3 = new FillTransition(Duration.millis(500), dot3, Color.LIGHTGREY, Color.BLACK);
        ParallelTransition par2 = new ParallelTransition(unfillDot2, fillDot3);

        SequentialTransition seq = new SequentialTransition(par1, par2);
        seq.setCycleCount(Animation.INDEFINITE);
        seq.setAutoReverse(true);
        seq.play();


        /*Adding Containers and showing scene
        * ----------------------------------------------------------------*/
        vboxProcess.getChildren().addAll(headerImage, group, serverImage);
        vboxProcess.setAlignment(Pos.CENTER);
        vboxProcess.setSpacing(20);

        Scene loginProcess = new Scene(vboxProcess, 250, 400);
        loginStage.setScene(loginProcess);


//        TODO Buffer for Login
        Buffer buffer = new Buffer();
        Thread wait = new Thread(buffer);
        wait.start();
    }

    public void loginSuccessfull(){
        VBox vboxSuccess = new VBox();

        /*Load Success Image
         * ----------------------------------------*/
        try {
            String url = System.getProperty("user.dir");
            url += "/src/Image/success_robo.png";
            File imageSuccess = new File(url);
            url = imageSuccess.toURI().toURL().toString();
            Image success = new Image(url);
            ImageView successImage = new ImageView(success);
            successImage.setFitHeight(70);
            successImage.setFitWidth(70);
            successImage.getStyleClass().add("headerIcon");
            vboxSuccess.getChildren().add(successImage);
        } catch (Exception e){
            logger.warning("Image could not be loaded. \n Stack Trace: "+e.getStackTrace().toString());
        }

        Label sucessLabel = new Label(tr.getString("labels.loginSuccessfull"));
        Button close = new Button(tr.getString("buttons.close"));
        close.getStyleClass().add("secondary");
        sucessLabel.getStyleClass().add("defaultLabel");

        vboxSuccess.getChildren().addAll(sucessLabel, close);
        vboxSuccess.setAlignment(Pos.CENTER);
        vboxSuccess.setSpacing(20);

        Scene loginSuccessful = new Scene(vboxSuccess, 250, 400);

        String stylesheet = getClass().getResource("stylesheet.css").toExternalForm();
        loginSuccessful.getStylesheets().add(stylesheet);

        loginStage.setScene(loginSuccessful);

        /*Listeners for Button presses
        * ---------------------------------*/
        close.setOnAction(click -> {
            this.stop();
        });
    }


    public class Buffer implements Runnable {

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(7);
                Platform.runLater(() -> {
                    loginSuccessfull();
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
