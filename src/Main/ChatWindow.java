package Main;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Logger;

public class ChatWindow extends ScrollPane {
    VBox chatHistory;
    VBox message, messageTime;
    Label messageText, time;
    View view;
    Translator tr = ServiceLocator.getServiceLocator().getTranslator();
    Logger logger = ServiceLocator.getServiceLocator().getLogger();


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
            logger.info("No messages to display.");
        } else {
            for (Message message : messageList) {
                displayNewMessage(message);
                logger.finest("Message |"+message.getMessage()+"| handovered");
            }
        }

    }


    public void displayNewMessage(Message msg){
        VBox messageBoxContainer = new VBox();
        messageBoxContainer.prefWidthProperty().bind(chatHistory.widthProperty());

        //Create Subcontainers (Bubble and TimeBox)
        try {
            message = new VBox();
            messageTime = new VBox();
            messageBoxContainer.getChildren().addAll(message, messageTime);
        } catch (Exception e){
            logger.warning("Message could not be created.");
        }

        //Create Time Box
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("kk:mm");
            time = new Label(msg.getMessageTimeStamp().format(formatter));
            time.getStyleClass().add("MessageBubbleTime");
            messageTime.getChildren().add(time);
        } catch (Exception e){
            logger.warning("TimeStamp could not be created.");
        }

        //Create Message Bubble
        messageText = new Label(msg.getMessage());
        messageText.getStyleClass().add("MessageBubbleText");
        message.getChildren().add(messageText);
        message.setMinHeight(30);

        //Message Bubble and TimeBox Styling
        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
        Font font = new Font("Tahoma", Font.PLAIN, 10);
        int textwidth = (int)(font.getStringBounds(msg.getMessage(), frc).getWidth());
        logger.finest("Calculated message width: "+textwidth);
        if (textwidth >= 220){
            ErrorPopUp errorPopUp = new ErrorPopUp(tr.getString("ErrorMessages.messageTooLong"), tr.getString("buttons.close"));
            logger.info("Calculated message width: "+textwidth+" extends defined limit");
        } else {
            message.setMinWidth(textwidth + 50);
            message.setMaxWidth(textwidth + 50);
            if (msg.isReceived()) {
                message.getStyleClass().add("MessageBubbleReceived");
                messageTime.getStyleClass().add("MessageBubbleTimeReceived");
                messageBoxContainer.setId("MessageBubbleContainerReceived"); //TODO
            } else {
                message.getStyleClass().add("MessageBubbleSent");
                messageTime.getStyleClass().add("MessageBubbleTimeSent");
                messageBoxContainer.setId("MessageBubbleContainerSent"); //TODO
            }
            chatHistory.getChildren().add(messageBoxContainer);
        }
    }


}
