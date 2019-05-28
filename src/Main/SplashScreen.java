package Main;

import javafx.animation.FillTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
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
import java.util.concurrent.TimeUnit;

public class SplashScreen extends VBox {

    ImageView headerImage;
    private Stage splashStage;
    private Initializer initializer;

    public SplashScreen(){

        splashStage = new Stage();
        Scene scene = new Scene(this, 500, 350);
        splashStage.setScene(scene);
        splashStage.setAlwaysOnTop(true);
        splashStage.initStyle(StageStyle.UNDECORATED);

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
            this.getChildren().add(headerImage);
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

        this.getChildren().add(group);
        this.setSpacing(20);
        this.setAlignment(Pos.TOP_CENTER);

        splashStage.show();

        //Buffer with Thread for chaging scene
        Buffer buffer = new Buffer();
        Thread t = new Thread(buffer);
        t.start();

        initializer = new Initializer();

    }

    private void showLoginOptions(){

        VBox vBox = new VBox();

        //Create Fields and Buttons for Login
        Button login = new Button("Login");
        Button createAccount = new Button("Create Account");
        login.getStyleClass().add("primary");
        createAccount.getStyleClass().add("secondary");

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
        splashStage.setScene(openScene);

        String stylesheet = getClass().getResource("stylesheet.css").toExternalForm();
        openScene.getStylesheets().add(stylesheet);


        //Listeners for choice made
        login.setOnAction(click -> {
            initializer.ready(true);
            splashStage.hide();
        });


    }

    private class Buffer implements Runnable {

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(5);
                Platform.runLater(() -> {
                    showLoginOptions();
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
