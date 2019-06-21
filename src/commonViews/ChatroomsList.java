package src.commonViews;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import src.ServiceLocator;
import src.commonClasses.Translator;

import java.io.IOException;
import java.util.logging.Logger;

public class ChatroomsList {
    Stage chatroomsListStage;
    public ListView<String> chatroomsList;
    ServiceLocator sl;
    Logger logger;

    public ChatroomsList(){
        sl =  ServiceLocator.getServiceLocator();
        logger = sl.getLogger();

        chatroomsListStage = new Stage();
        //chatroomsListStage.initStyle(StageStyle.UNDECORATED);
        VBox vbox = new VBox();

        Label popupMessage = new Label("Select a Chatroom");

        chatroomsList = new ListView<>();
        getChatroomsList();

        vbox.getChildren().addAll(popupMessage, chatroomsList);
        vbox.setSpacing(30);
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox,400,250);

        scene.getStylesheets().add(sl.getClass().getResource("stylesheet.css").toExternalForm());

        chatroomsListStage.setScene(scene);
        chatroomsListStage.setTitle("Select a Chatroom");
        chatroomsListStage.centerOnScreen();
        chatroomsListStage.setAlwaysOnTop(true);
        chatroomsListStage.setMinWidth(400);
        chatroomsListStage.setMinHeight(250);
        chatroomsListStage.setMaxWidth(400);
        chatroomsListStage.setMaxHeight(250);
        chatroomsListStage.show();
    }

    private void getChatroomsList() {
        try {
            String[] chatrooms =  sl.getChatClient().listChatrooms();

            for (String chatroomName : chatrooms) {
                chatroomsList.getItems().add(chatroomName);
            }
        } catch (IOException e) {
            Translator tr = ServiceLocator.getServiceLocator().getTranslator();

            ErrorPopUp errorPopUp = new ErrorPopUp(tr.getString("ErrorMessages.serverError") + " " + e.getMessage(),
                    tr.getString("buttons.close"));
        }
    }

    public void stop() {
        this.chatroomsListStage.close();
    }
}
