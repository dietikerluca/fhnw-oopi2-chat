package Main;

import Abstract_Classes.View;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.File;
import java.util.logging.Logger;

public class Login_View extends View {

    private ServiceLocator sl;
    private Translator tr;
    private Logger logger;

    public TextField usernameField;
    public PasswordField passwordField;
    public Button confirmButton, closeSuccess, closeTryAgain;

    private VBox vbox;
    private Main_Model model;
    private Label usernameLabel, pwLabel;

    private Image errorRobo, server;
    private File image;
    private ImageView headerImage, serverImage;
    private String url;

    private Scene loginProcess, loginSuccessful, loginNotSuccessful, scene;


    public Login_View(Stage stage, Login_Model model) {
        super(stage, model);
        stage.setAlwaysOnTop(true);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setAlwaysOnTop(true);
        stage.setMinHeight(400);
        stage.setMinWidth(250);
        stage.setTitle(ServiceLocator.getServiceLocator().getTranslator().getString("windows.login"));

        ServiceLocator.getServiceLocator().getLogger().info("Login View initialized");
    }


    public Scene create_GUI(){
        sl = ServiceLocator.getServiceLocator();
        tr = sl.getTranslator();
        logger = sl.getLogger();

        StackPane root = new StackPane();
        vbox = new VBox();

        //Create Scene
        scene = new Scene(root, 250, 400);

        /*Load Image
         * ----------------------------------------*/
        try {
            url = System.getProperty("user.dir");
            url += "/src/Image/login_robo.png";
            image = new File(url);
            url = image.toURI().toURL().toString();
            errorRobo = new Image(url);
            headerImage = new ImageView(errorRobo);
            headerImage.setFitHeight(70);
            headerImage.setFitWidth(70);
            headerImage.getStyleClass().add("headerIcon");
            vbox.getChildren().add(headerImage);
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
        root.getChildren().add(vbox);
        root.setAlignment(vbox, Pos.CENTER);
        vbox.setSpacing(20);
        vbox.setAlignment(Pos.CENTER);

        String stylesheet = getClass().getResource("stylesheet.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);

        /*Preparation for Login Sequence
        *------------------------------ */
        createLoginSequence_GUI();
        createLoginSuccessfull_GUI();
        createLoginNotSuccessfull_GUI();

        return scene;
    }

    private void createLoginSequence_GUI(){
        VBox vboxProcess = new VBox();
        Group group = new Group();

        /*Load Server Image
         * ----------------------------------------*/
        try {
            url = System.getProperty("user.dir");
            url += "/src/Image/server.png";
            image = new File(url);
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

        loginProcess = new Scene(vboxProcess, 250, 400);
    }

    private void createLoginSuccessfull_GUI(){
        VBox vboxSuccess = new VBox();
        loginSuccessful = new Scene(vboxSuccess, 250, 400);

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

        Label successLabel = new Label(tr.getString("labels.loginSuccessfull"));
        closeSuccess = new Button(tr.getString("buttons.close"));
        closeSuccess.getStyleClass().add("secondary");
        successLabel.getStyleClass().add("defaultLabel");

        vboxSuccess.getChildren().addAll(successLabel, closeSuccess);
        vboxSuccess.setAlignment(Pos.CENTER);
        vboxSuccess.setSpacing(20);

        String stylesheet = getClass().getResource("stylesheet.css").toExternalForm();
        loginSuccessful.getStylesheets().add(stylesheet);

    }

    private void createLoginNotSuccessfull_GUI(){
        VBox vboxNoSuccess = new VBox();
        loginNotSuccessful = new Scene(vboxNoSuccess, 250, 400);

        /*Load No Success Image
         * ----------------------------------------*/
        try {
            String url = System.getProperty("user.dir");
            url += "/src/Image/surprized_question_robo.png";
            File imageNoSuccess = new File(url);
            url = imageNoSuccess.toURI().toURL().toString();
            Image noSuccess = new Image(url);
            ImageView noSuccessImage = new ImageView(noSuccess);
            noSuccessImage.setFitHeight(70);
            noSuccessImage.setFitWidth(70);
            noSuccessImage.getStyleClass().add("headerIcon");
            vboxNoSuccess.getChildren().add(noSuccessImage);
        } catch (Exception e){
            logger.warning("Image could not be loaded. \n Stack Trace: "+e.getStackTrace().toString());
        }

        Label noSuccessLabel = new Label(tr.getString("labels.loginNotSuccessfull"));
        closeTryAgain = new Button(tr.getString("buttons.tryAgain"));
        closeTryAgain.getStyleClass().add("secondary");
        noSuccessLabel.getStyleClass().add("defaultLabel");

        vboxNoSuccess.getChildren().addAll(noSuccessLabel, closeTryAgain);
        vboxNoSuccess.setAlignment(Pos.CENTER);
        vboxNoSuccess.setSpacing(20);

        String stylesheet = getClass().getResource("stylesheet.css").toExternalForm();
        loginNotSuccessful.getStylesheets().add(stylesheet);
    }

    public Scene getLoginProcess(){
        return loginProcess;
    }

    public Scene getLoginSuccessful(){
        return loginSuccessful;
    }

    public Scene getLoginNotSuccessful(){
        return loginNotSuccessful;
    }

    public Scene getScene(){
        return scene;
    }

}
