package src.commonViews;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import src.ServiceLocator;
import java.util.logging.Logger;

public class ChoicePopUp {
    public Stage popUpStage;
    public Button primaryBtn, secondaryBtn;
    ServiceLocator sl;
    Logger logger;

    public ChoicePopUp(String popUpMessageString, String buttonPrimary, String buttonMessageSecondary, String window){
        sl = ServiceLocator.getServiceLocator();
        logger = sl.getLogger();
        popUpStage = new Stage();
        popUpStage.initStyle(StageStyle.UNDECORATED);
        VBox vbox = new VBox();

        // Load header image
        try {
            ImageView headerImageView = ImageLoader.loadImageView("/src/assets/img/surprized_question_robo.png", 70,70, "headerIcon");
            vbox.getChildren().add(headerImageView);
        } catch (Exception e){
            logger.warning("image could not be loaded. \n Stack Trace: " + e);
        }

        Label popupMessage = new Label(popUpMessageString);
        popupMessage.getStyleClass().add("errorMessage");

        primaryBtn = new Button(buttonPrimary);
        primaryBtn.getStyleClass().add("primary");

        secondaryBtn = new Button(buttonMessageSecondary);
        secondaryBtn.getStyleClass().add("secondary");

        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.getChildren().addAll(primaryBtn, secondaryBtn);
        hbox.setAlignment(Pos.CENTER);

        vbox.getChildren().addAll(popupMessage, hbox);
        vbox.setSpacing(30);
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox,400,250);
        scene.getStylesheets().add(sl.getClass().getResource("stylesheet.css").toExternalForm());

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
        sl = ServiceLocator.getServiceLocator();
        logger = sl.getLogger();
        popUpStage = new Stage();
        popUpStage.initStyle(StageStyle.UNDECORATED);
        VBox vbox = new VBox();

        // Load header image
        try {
            ImageView headerImageView = ImageLoader.loadImageView("/src/assets/img/surprized_question_robo.png", 70,70, "headerIcon");
            vbox.getChildren().add(headerImageView);
        } catch (Exception e){
            logger.warning("image could not be loaded. \n Stack Trace: " + e);
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
        String stylesheet = sl.getClass().getResource("stylesheet.css").toExternalForm();
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
