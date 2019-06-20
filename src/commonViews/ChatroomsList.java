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

public class ChatroomsList {
    Stage chatroomsListStage;
    public Button primaryBtn, secondaryBtn;
    ServiceLocator sl;
    Logger logger = sl.getLogger();

    public ChatroomsList(){
        sl =  ServiceLocator.getServiceLocator();

        chatroomsListStage = new Stage();
        //chatroomsListStage.initStyle(StageStyle.UNDECORATED);
        VBox vbox = new VBox();

        Label popupMessage = new Label("Select a Chatroom");

        vbox.getChildren().addAll(popupMessage);
        vbox.setSpacing(30);
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox,400,250);

        scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());

        chatroomsListStage.setScene(scene);
        chatroomsListStage.setTitle("Select a Chatroom");
        chatroomsListStage.centerOnScreen();
        chatroomsListStage.setAlwaysOnTop(true);
        chatroomsListStage.setMinWidth(400);
        chatroomsListStage.setMinHeight(250);
        chatroomsListStage.setMaxWidth(400);
        chatroomsListStage.setMaxHeight(250);
        chatroomsListStage.show();

        chatroomsListStage.setOnCloseRequest(event -> {
            logger.info("User tried to close window.");
            event.consume();
        });

        primaryBtn.setOnAction(event -> {
            chatroomsListStage.close();
            logger.finest("Userchoice: Primary Button");
        });

        secondaryBtn.setOnAction(event -> {
            chatroomsListStage.close();
            logger.finest("Userchoice: Secondary Button");
        });
    }

    public void stop() {
        this.chatroomsListStage.close();
    }
}
