package Main;

import Abstract_Classes.Model;
import Abstract_Classes.View;
import javafx.animation.FillTransition;
import javafx.animation.SequentialTransition;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.File;

public class Splash_View extends View {

    private ImageView headerImage;
    private ServiceLocator sl;
    private String stylesheet;
    public Button login, createAccount;

    public Splash_View(Stage primaryStage, Model model){
        super(primaryStage, model);
        stage.setAlwaysOnTop(true);
        stage.initStyle(StageStyle.UNDECORATED);
    }

    @Override
    protected Scene create_GUI() {
        sl = ServiceLocator.getServiceLocator();

        VBox root = new VBox();
        root.getStyleClass().add("splashBox");
        Scene scene = new Scene(root, 500, 350);

        /*Load Image
         * ----------------------------------------*/
        try {
            String url = System.getProperty("user.dir");
            url += "/src/Image/launcher_Logo.png";
            File image = new File(url);
            url = image.toURI().toURL().toString();
            Image errorRobo = new Image(url);
            headerImage = new ImageView(errorRobo);
            headerImage.setFitHeight(250);
            headerImage.setFitWidth(500);
            headerImage.getStyleClass().add("headerIcon");
            root.getChildren().add(headerImage);
        } catch (Exception e){

        }

        /*Create Animation Dots
         * ----------------------------------------------------------------------*/
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


        /*Animation Setup
         * -------------------------------------------------------------------------------------------------------*/
        FillTransition fillDot1 = new FillTransition(Duration.millis(500), dot1, Color.LIGHTGREY, Color.BLACK);
        fillDot1.setDelay(Duration.millis(300));
        FillTransition fillDot2 = new FillTransition(Duration.millis(500), dot2, Color.LIGHTGREY, Color.BLACK);
        fillDot2.setDelay(Duration.millis(100));
        FillTransition fillDot3 = new FillTransition(Duration.millis(500), dot3, Color.LIGHTGREY, Color.BLACK);
        fillDot3.setDelay(Duration.millis(1000));
        FillTransition fillDot4 = new FillTransition(Duration.millis(500), dot4, Color.LIGHTGREY, Color.BLACK);

        SequentialTransition seq = new SequentialTransition(fillDot1, fillDot2, fillDot3, fillDot4);
        seq.play();

        stylesheet = getClass().getResource("stylesheet.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);

        root.getChildren().add(group);
        root.setSpacing(20);
        root.setAlignment(Pos.TOP_CENTER);

        /*Preparation for Login Buttons Main_View
        * -------------------------------------------------*/
        //Create Fields and Buttons for Login
        login = new Button("Login");
        createAccount = new Button("Create Account");
        login.getStyleClass().add("primary");
        createAccount.getStyleClass().add("secondary");

        return scene;
    }

    /*Creates Scene with Login Options
    * ----------------------------------------------------------------*/
    public Scene createLoginOption_GUI(){
        /*Preparation for Loaded LoginScreen TODO Problems in here
         * --------------------------------------------------*/
        VBox vBox = new VBox();

        //HBox for Buttons
        HBox hbox = new HBox();
        hbox.getChildren().addAll(login, createAccount);
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER);

        //Add Items to vBox for Scene
        vBox.getChildren().addAll(headerImage, hbox);
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.TOP_CENTER);

        Scene openScene = new Scene(vBox, 500, 350);
        openScene.getStylesheets().add(stylesheet);
        //------------------------------------------------------

        return openScene;
    }


}
