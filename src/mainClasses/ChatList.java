package src.mainClasses;

import javafx.collections.ListChangeListener;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import src.ServiceLocator;
import src.typeClasses.Chat;

import java.util.logging.Logger;


public class ChatList extends ScrollPane {
    ServiceLocator sl;
    Logger logger;

    Main_Model model;
    ListView chatList;

    public ChatList(Main_Model mainModel){
        this.model = mainModel;

        sl = ServiceLocator.getServiceLocator();
        logger = sl.getLogger();

        chatList = new ListView<ChatListElement>();
        chatList.prefWidthProperty().bind(this.widthProperty());
        chatList.prefHeightProperty().bind(this.heightProperty());

        model.chats.addListener((ListChangeListener<Chat>) c -> updateChatList());
        updateChatList();

        this.setId("ContactsWindowScrollPane");
        this.chatList.setId("ContactsWindowList");
        this.setContent(chatList);
    }


    public Chat getSelectedChat() {
        ChatListElement chatListElement = (ChatListElement) chatList.getSelectionModel().getSelectedItem();
        return chatListElement.getChat();
    }

    public Chat getFocusedChat() {
        ChatListElement chatListElement = (ChatListElement) chatList.getFocusModel().getFocusedItem();
        return chatListElement.getChat();
    }

    public ListView getChatList() {
        return chatList;
    }

    public boolean isEmpty(){
        return chatList.getItems().isEmpty();
    }

    public void updateChatList(){
        chatList.getItems().clear();
        logger.fine("Contact list cleared");
        logger.fine("Re-creating contact list.");

        for (Chat c : model.getChats()){
            logger.finest("Adding List element for Chat: " + c.getName());
            chatList.getItems().add(new ChatListElement(c));
        }
        logger.fine("Update finished.");
    }
}
