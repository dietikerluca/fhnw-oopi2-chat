package src.mainClasses;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import src.ServiceLocator;
import src.commonClasses.Translator;
import src.commonViews.ErrorPopUp;
import src.typeClasses.Chat;
import src.typeClasses.Message;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Logger;

public class ChatWindow extends ScrollPane {
    Main_Model model;
    VBox chatHistory;

    ServiceLocator sl;
    Translator tr;
    Logger logger;

    public ChatWindow(Main_Model model){
        this.model = model;
        sl = ServiceLocator.getServiceLocator();
        tr = sl.getTranslator();
        logger = sl.getLogger();

        chatHistory = new VBox();
        chatHistory.prefWidthProperty().bind(this.widthProperty());
        chatHistory.heightProperty().addListener(change -> {
            this.setHvalue(1);
            this.setVvalue(1);
        });

        updateChatWindow();

        this.setContent(chatHistory);
        this.setId("ScrollPaneMessagesBubbles");
        this.setFitToWidth(true);
    }

    public void updateChatWindow() {
        Platform.runLater(() -> {
            chatHistory.getChildren().clear();
        });

        Chat currentChat = model.getCurrentChat();
        if (currentChat != null) {
            for (Message message : currentChat.getMessages()) {
                Platform.runLater(() -> {
                    chatHistory.getChildren().add(createMessageElement(message));
                });
            }
        }
    }

    public VBox createMessageElement(Message message) {
        VBox container = new VBox();
        container.prefWidthProperty().bind(chatHistory.widthProperty());


        // Sender Text above bubble
        VBox senderBox = new VBox();
        Label senderLabel;
        if (message.getPerson() != null && !message.getPerson().getFirstname().isEmpty()) {
            senderLabel = new Label(message.getPerson().getFirstname() + " " + message.getPerson().getLastname());
        } else {
            senderLabel = new Label(message.getUsername());
        }
        senderLabel.getStyleClass().add("MessageBubbleTime");
        senderBox.getChildren().add(senderLabel);

        // Message Bubble
        VBox messageBox = new VBox();
        messageBox.setFillWidth(false);
        messageBox.setAlignment(Pos.CENTER_RIGHT);

        Label messageText = new Label(message.getMessage());
        messageText.getStyleClass().add("MessageBubbleText");
        messageBox.getChildren().add(messageText);
        messageBox.setMinHeight(30);

        // Format Date/Time and add to container
        VBox messageTimeBox = new VBox();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("kk:mm");
        Label timeLabel = new Label(message.getMessageTimeStamp().format(formatter));
        timeLabel.getStyleClass().add("MessageBubbleTime");
        messageTimeBox.getChildren().add(timeLabel);

        // Add elements
        container.getChildren().addAll(senderBox, messageBox, messageTimeBox);

        if (message.isReceived()) {
            messageBox.getStyleClass().add("MessageBubbleReceived");
            messageTimeBox.getStyleClass().add("MessageBubbleTimeReceived");
            container.setId("MessageBubbleContainerReceived");
        } else {
            messageBox.getStyleClass().add("MessageBubbleSent");
            messageTimeBox.getStyleClass().add("MessageBubbleTimeSent");
            senderBox.getStyleClass().add("MessageBubbleTimeSent");
            container.setId("MessageBubbleContainerSent");
        }

        return container;
    }

//    public void displayNewMessage(Message msg){
        //Message Bubble and TimeBox Styling
//        AffineTransform affinetransform = new AffineTransform();
//        FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
//        Font font = new Font("Tahoma", Font.PLAIN, 10);

//        int textwidth = (int)(font.getStringBounds(msg.getMessage(), frc).getWidth());
//        logger.finest("Calculated message width: "+textwidth);
//        if (textwidth >= 220){
//            ErrorPopUp errorPopUp = new ErrorPopUp(tr.getString("ErrorMessages.messageTooLong"), tr.getString("buttons.close"));
//            logger.info("Calculated message width: "+textwidth+" extends defined limit");
//        } else {
//            message.setMinWidth(textwidth + 50);
//            message.setMaxWidth(textwidth + 50);

//            chatHistory.getChildren().add(messageBoxContainer);
//        }
//    }
}
