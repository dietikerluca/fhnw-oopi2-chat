package src.commonViews;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import src.ServiceLocator;

import java.io.File;
import java.util.logging.Logger;

public class ChoicePopUp {

    Stage popUpStage;
    public Button primaryBtn, secondaryBtn;
    ServiceLocator sl = ServiceLocator.getServiceLocator();
    Logger logger = sl.getLogger();

    public ChoicePopUp(String popUpMessageString, String buttonPrimary, String buttonMessageSecondary, String window){
        popUpStage = new Stage();
        popUpStage.initStyle(StageStyle.UNDECORATED);
        VBox vbox = new VBox();

        /*Load src.assets
         * ----------------------------------------*/
        try {
            String url = System.getProperty("user.dir");
            url += "/src/assets/img/surprized_question_robo.png";
            File image = new File(url);
            url = image.toURI().toURL().toString();
            Image errorRobo = new Image(url);
            ImageView headerImage = new ImageView(errorRobo);
            headerImage.setFitHeight(70);
            headerImage.setFitWidth(70);
            headerImage.getStyleClass().add("headerIcon");
            vbox.getChildren().add(headerImage);
        } catch (Exception e){
            logger.warning("Unable to load image for Choice Pop Up \n " +
                    "Stack Trace: "+e.getStackTrace().toString());
        }
        Label errorMessage = new Label(popUpMessageString);
        errorMessage.getStyleClass().add("errorMessage");
        primaryBtn = new Button(buttonPrimary);
        secondaryBtn = new Button(buttonMessageSecondary);
        primaryBtn.getStyleClass().add("primary");
        secondaryBtn.getStyleClass().add("secondary");
        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.getChildren().addAll(primaryBtn, secondaryBtn);
        hbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(errorMessage, hbox);
        vbox.setSpacing(30);
        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox,400,250);
        String stylesheet = getClass().getResource("stylesheet.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);
        popUpStage.setScene(scene);
        popUpStage.setTitle(window);
        popUpStage.centerOnScreen();
        popUpStage.setAlwaysOnTop(true);
        popUpStage.setMinWidth(400);
        popUpStage.setMinHeight(250);
        popUpStage.setMaxWidth(400);
        popUpStage.setMaxHeight(250);
        popUpStage.show();
        popUpStage.setOnCloseRequest(event -> {
            logger.info("User tried to close window.");
            event.consume();
        });

        primaryBtn.setOnAction(event -> {
            popUpStage.close();
            logger.finest("Userchoice: Primary Button");
        });

        secondaryBtn.setOnAction(event -> {
            popUpStage.close();
            logger.finest("Userchoice: Secondary Button");
        });

    }

    public ChoicePopUp(String popUpMessageString, String buttonMessageSecondary, String window){
        popUpStage = new Stage();
        VBox vbox = new VBox();

        /*Load src.assets
         * ----------------------------------------*/
        try {
            String url = System.getProperty("user.dir");
            url += "/src/assets/img/surprized_question_robo.png";
            File image = new File(url);
            url = image.toURI().toURL().toString();
            Image errorRobo = new Image(url);
            ImageView headerImage = new ImageView(errorRobo);
            headerImage.setFitHeight(70);
            headerImage.setFitWidth(70);
            headerImage.getStyleClass().add("headerIcon");
            vbox.getChildren().add(headerImage);
        } catch (Exception e){
            logger.warning("Unable to load image for Choice Pop Up \n " +
                    "Stack Trace: "+e.getStackTrace().toString());
        }
        Label errorMessage = new Label(popUpMessageString);
        errorMessage.getStyleClass().add("errorMessage");
        secondaryBtn = new Button(buttonMessageSecondary);
        secondaryBtn.getStyleClass().add("secondary");
        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.getChildren().add(secondaryBtn);
        hbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(errorMessage, hbox);
        vbox.setSpacing(30);
        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox,400,250);
        String stylesheet = getClass().getResource("stylesheet.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);
        popUpStage.setScene(scene);
        popUpStage.setTitle(window);
        popUpStage.centerOnScreen();
        popUpStage.setAlwaysOnTop(true);
        popUpStage.setMinWidth(400);
        popUpStage.setMinHeight(250);
        popUpStage.setMaxWidth(400);
        popUpStage.setMaxHeight(250);
        popUpStage.show();
        popUpStage.setOnCloseRequest(event -> {
            logger.info("User tried to close window.");
            event.consume();
        });

        secondaryBtn.setOnAction(event -> {
            popUpStage.close();
            logger.finest("Userchoice: Secondary Button");
        });

    }

    public void stop() {
        this.popUpStage.close();
    }
}
