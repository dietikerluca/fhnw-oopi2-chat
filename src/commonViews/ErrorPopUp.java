package src.commonViews;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import src.ServiceLocator;

import java.io.File;
import java.util.logging.Logger;

public class ErrorPopUp {

    Stage errorStage;
    Logger logger = ServiceLocator.getServiceLocator().getLogger();

    public ErrorPopUp(String errorMessageString, String buttonMessage){
        ServiceLocator sl = ServiceLocator.getServiceLocator();

        errorStage = new Stage();
        Stage errorStage = new Stage();
        VBox vbox = new VBox();

        /*Load src.assets
        * ----------------------------------------*/
        try {
            String url = System.getProperty("user.dir");
            url += "/src/assets/img/sad_error_robo.png";
            File image = new File(url);
            url = image.toURI().toURL().toString();
            Image errorRobo = new Image(url);
            ImageView headerImage = new ImageView(errorRobo);
            headerImage.setFitHeight(70);
            headerImage.setFitWidth(70);
            headerImage.getStyleClass().add("headerIcon");
            vbox.getChildren().add(headerImage);
        } catch (Exception e){
            logger.warning("src.assets could not be loaded.");
        }
        Label errorMessage = new Label(errorMessageString);
        errorMessage.getStyleClass().add("errorMessage");
        Button closeBtn = new Button(buttonMessage);
        closeBtn.getStyleClass().add("close");
        vbox.getChildren().addAll(errorMessage, closeBtn);
        vbox.setSpacing(30);
        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox,250,300);
        String stylesheet = sl.getClass().getResource("stylesheet.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);
        errorStage.setScene(scene);
        errorStage.setTitle("Error occurred");
        errorStage.centerOnScreen();
        errorStage.setAlwaysOnTop(true);
        errorStage.setMinWidth(250);
        errorStage.setMinHeight(300);
        errorStage.setMaxWidth(250);
        errorStage.setMaxHeight(350);
        errorStage.show();
        closeBtn.setOnAction(event -> {
            errorStage.close();
            logger.finest("User closed window.");
        });
    }
}
