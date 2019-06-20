package src.mainClasses;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import src.ServiceLocator;
import src.commonViews.ImageLoader;
import src.typeClasses.Chat;
import src.typeClasses.Chatroom;
import src.typeClasses.Person;
import src.typeClasses.PrivateChat;

import java.io.File;
import java.util.logging.Logger;

public class ChatListElement extends HBox {
    ServiceLocator sl = ServiceLocator.getServiceLocator();
    Logger logger = sl.getLogger();

    Chat chat;
    Person person;

    VBox contactInfoContainer;
    Label name, secondText;
    Ellipse status;

    public ChatListElement(Chat chat){
        this.chat = chat;

        if (chat instanceof PrivateChat) {
            // Private Chat
            this.person = ((PrivateChat) chat).getPerson();

            if (!person.getFirstname().isEmpty()) {
                this.name = new Label(person.getFirstname() + " " + person.getLastname());
            } else {
                this.name = new Label(person.getUsername());
            }

            this.secondText = new Label("Last seen: 22.06.2019"); //TODO
        } else {
            // Chatroom
            this.name = new Label(chat.getName());
            this.secondText = new Label("Last seen: 22.06.2019"); //TODO
        }

        if (person != null && person.isBlocked()) {
            // Load Blocked Icon
            try {
                ImageView blockedImageView = ImageLoader.loadImageView("/src/assets/img/block_black.png", 30,30, "headerIcon");
                this.getChildren().add(blockedImageView);
            } catch (Exception e){
                logger.warning("image could not be loaded. \n Stack Trace: " + e);
            }

            this.name.getStyleClass().add("TextChatListElementBlocked");
            this.secondText.getStyleClass().add("TextChatListElementBlocked");

        } else {
            this.status = new Ellipse(5,5); //TODO
            this.status.setFill(Color.GREEN); //TODO
            this.name.getStyleClass().add("TextChatListElement");
            this.secondText.getStyleClass().add("TextChatListElementSecond");
            this.getChildren().add(status);
        }

        this.getStyleClass().add("ChatListElement");
        this.contactInfoContainer = new VBox();
        this.contactInfoContainer.getStyleClass().add("ChatListElementTextContainer");
        this.contactInfoContainer.getChildren().addAll(this.name, this.secondText);
        this.getChildren().add(this.contactInfoContainer);

    }

    public Chat getChat() {
        return chat;
    }
}