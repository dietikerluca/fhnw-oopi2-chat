package src.splashScreen;

import javafx.animation.FillTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import src.ServiceLocator;
import src.abstractClasses.Model;
import src.abstractClasses.View;
import src.commonClasses.Translator;
import src.commonViews.ImageLoader;

import java.util.logging.Logger;

public class Splash_View extends View {
    private ServiceLocator sl;
    private Logger logger;
    private String stylesheet;
    private Translator translator;

    ImageView headerImageView;
    protected Button login, createAccount;
    protected Hyperlink changeServer;

    public Splash_View(Stage primaryStage, Model model){
        super(primaryStage, model);
        stage.setAlwaysOnTop(true);
        stage.initStyle(StageStyle.UNDECORATED);
    }

    @Override
    protected Scene create_GUI() {
        sl = ServiceLocator.getServiceLocator();
        logger = sl.getLogger();

        VBox root = new VBox();
        root.getStyleClass().add("splashBox");
        Scene scene = new Scene(root, 500, 350);

        // Load header image
        try {
            headerImageView = ImageLoader.loadImageView("/src/assets/img/launcher_Logo.png", 250,500, "headerIcon");
            root.getChildren().add(headerImageView);
        } catch (Exception e){
            logger.warning("image could not be loaded. \n Stack Trace: " + e);
        }

        root.getChildren().add(createLoadAnimation());
        root.setSpacing(20);
        root.setAlignment(Pos.TOP_CENTER);

        // Create Fields and Buttons for Login
        login = new Button("Login");
        createAccount = new Button("Create Account");
        changeServer = new Hyperlink();
        changeServer.setText("Change default Server");

        login.getStyleClass().add("primary");
        createAccount.getStyleClass().add("secondary");
        changeServer.getStyleClass().add("hyperlink"); //TODO

        // Load stylesheet
        stylesheet = sl.getClass().getResource("stylesheet.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);

        return scene;
    }

    public Scene createLoginOption_GUI(){
        VBox vBox = new VBox();

        // HBox for Buttons
        HBox hbox = new HBox();
        hbox.getChildren().addAll(login, createAccount);
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER);

        // VBox for Changing Server
        VBox serverBox = new VBox();
        serverBox.getChildren().add(changeServer);
        serverBox.setAlignment(Pos.CENTER);


        // Add Items to vBox for Scene
        vBox.getChildren().addAll(headerImageView, hbox, serverBox);
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.TOP_CENTER);

        Scene openScene = new Scene(vBox, 500, 390);
        openScene.getStylesheets().add(stylesheet);

        return openScene;
    }

    private Group createLoadAnimation() {
        final int SPEED = 300;

        // Initialize circles
        Ellipse dot1 = new Ellipse(50, 50,7, 7);
        Ellipse dot2 = new Ellipse(80, 50,7, 7);
        Ellipse dot3 = new Ellipse(110, 50,7, 7);
        Ellipse dot4 = new Ellipse(140, 50,7, 7);
        dot1.setFill(Color.LIGHTGREY);
        dot2.setFill(Color.LIGHTGREY);
        dot3.setFill(Color.LIGHTGREY);
        dot4.setFill(Color.LIGHTGREY);
        Group group = new Group();
        group.getChildren().addAll(dot1, dot2, dot3, dot4);

        // Create Transitions
        FillTransition fillDot1 = new FillTransition(Duration.millis(SPEED), dot1, Color.LIGHTGREY, Color.BLACK);
        fillDot1.setDelay(Duration.millis(SPEED));
        FillTransition fillDot2 = new FillTransition(Duration.millis(SPEED), dot2, Color.LIGHTGREY, Color.BLACK);
        fillDot2.setDelay(Duration.millis(SPEED));
        FillTransition fillDot3 = new FillTransition(Duration.millis(SPEED), dot3, Color.LIGHTGREY, Color.BLACK);
        fillDot3.setDelay(Duration.millis(SPEED));
        FillTransition fillDot4 = new FillTransition(Duration.millis(SPEED), dot4, Color.LIGHTGREY, Color.BLACK);

        SequentialTransition seq = new SequentialTransition(fillDot1, fillDot2, fillDot3, fillDot4);
        seq.setCycleCount(Timeline.INDEFINITE);
        seq.play();

        return group;
    }
}
