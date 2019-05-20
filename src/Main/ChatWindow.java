package Main;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sun.font.TextLabel;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ChatWindow extends ScrollPane {
    VBox chatHistory;
    VBox message;
    Label messageText;
    View view;


//    Default Constructor
    public ChatWindow(View view){
        this.view = view;
        chatHistory = new VBox();
        chatHistory.prefWidthProperty().bind(this.widthProperty());
        this.setContent(chatHistory);
        this.setId("ScrollPaneMessagesBubbles");
        this.setFitToWidth(true);
        chatHistory.heightProperty().addListener(change -> {
            this.setHvalue(1);
            this.setVvalue(1);
        });
    }

    public void displayMessages(ArrayList<Message> messageList){
        chatHistory = new VBox();
        chatHistory.prefWidthProperty().bind(this.widthProperty());
        this.setContent(chatHistory);
        this.setId("ScrollPaneMessagesBubbles");
        this.setFitToWidth(true);
        chatHistory.heightProperty().addListener(change -> {
            this.setHvalue(1);
            this.setVvalue(1);
        });
        if (messageList.isEmpty()){

        } else {
            for (Message message : messageList) {
                displayNewMessage(message);
            }
        }

    }


    public void displayNewMessage(Message msg){
        HBox messageBoxContainer = new HBox();
        messageBoxContainer.prefWidthProperty().bind(chatHistory.widthProperty());
        message = new VBox();
        messageBoxContainer.getChildren().add(message);
        messageText = new Label(msg.getMessage());
        messageText.setId("MessageText");
        message.getChildren().add(messageText);
        message.setMinHeight(30);
        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
        Font font = new Font("Tahoma", Font.PLAIN, 12);
        int textwidth = (int)(font.getStringBounds(msg.getMessage(), frc).getWidth());
        if (textwidth >= 220){
            ErrorPopUp errorPopUp = new ErrorPopUp("Message too long.");
        } else {
            message.setPrefWidth(textwidth + 50);
            if (msg.isReceived()) {
                message.setId("MessageBoxReceived");
                messageBoxContainer.setId("MessageBubbleContainerReceived");
            } else {
                message.setId("MessageBoxSent");
                messageBoxContainer.setId("MessageBubbleContainerSent");
            }
            chatHistory.getChildren().add(messageBoxContainer);
        }
    }


}
