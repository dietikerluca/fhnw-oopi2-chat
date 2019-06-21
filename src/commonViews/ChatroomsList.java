package src.commonViews;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import src.ServiceLocator;
import src.commonClasses.Translator;

import java.io.IOException;
import java.util.logging.Logger;

public class ChatroomsList {
    Stage chatroomsListStage;
    public ListView<String> chatroomsList;
    ServiceLocator sl;
    Logger logger;
    Translator tr;

    public ChatroomsList(){
        sl =  ServiceLocator.getServiceLocator();
        logger = sl.getLogger();
        tr = sl.getTranslator();

        chatroomsListStage = new Stage();
        //chatroomsListStage.initStyle(StageStyle.UNDECORATED);
        VBox vbox = new VBox();
        vbox.getStyleClass().add("windowPopUp");

        Label header = new Label(tr.getString("header.chatrooms"));
        Label label = new Label(tr.getString("labels.joinChatroom"));
        header.getStyleClass().add("header");
        label.getStyleClass().add("defaultLabel");

        chatroomsList = new ListView<>();
        chatroomsList.getStyleClass().add("chatroomsList");
        getChatroomsList();

        vbox.getChildren().addAll(header, label, chatroomsList);
        vbox.setSpacing(30);
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox,400,500);
        String stylesheet = sl.getClass().getResource("stylesheet.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);

        scene.getStylesheets().add(sl.getClass().getResource("stylesheet.css").toExternalForm());

        chatroomsListStage.setScene(scene);
        chatroomsListStage.setTitle("Select a Chatroom");
        chatroomsListStage.centerOnScreen();
        chatroomsListStage.setAlwaysOnTop(true);
        chatroomsListStage.setMinWidth(400);
        chatroomsListStage.setMinHeight(250);
        chatroomsListStage.setMaxWidth(400);
        chatroomsListStage.setMaxHeight(500);
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
