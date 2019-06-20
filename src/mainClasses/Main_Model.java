package src.mainClasses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import src.ServiceLocator;
import src.commonClasses.Translator;
import src.typeClasses.*;

public class Main_Model {
    ServiceLocator sl;
    Translator tr;
    String username;
    ObservableList<Person> persons;
    ObservableList<Chat> chats;
    Chat currentChat;

    public Main_Model(String username) {
        this.sl = ServiceLocator.getServiceLocator();
        this.tr = sl.getTranslator();
        this.username = username;

        persons = FXCollections.observableArrayList();
        chats = FXCollections.observableArrayList();
    }

    public void joinChatroom(String name) {
        Chatroom chatroom = null;
        // Try to find an existing chatroom
        for (Chat c : chats) if (c.getName().equals(name)) chatroom = (Chatroom) c;

        // Only join the chatroom if it doesn't already exist
        if (chatroom == null) {
            Chatroom newChatroom = new Chatroom(name);
            chats.add(newChatroom);
            sl.getChatClient().joinChatroom(newChatroom.getName(), username);
        }
    }

    public void sendMessage(Chat target, String msg) {
        Message message = new Message("brad", target.getName(), msg, false);
        target.addMessage(message);

        sl.getChatClient().sendMessage(message.getTarget(), message.getMessage());
    }

    public void receiveMessage(Message m) {
        if (!m.getUsername().equals(username)) { // Not my message
            boolean chatFound = false;
            for (Chat c : chats) {
                if (c.getName().equals(m.getTarget())) {
                    chatFound = true;
                    c.addMessage(m);
                }
            }

            if (!chatFound) {
                
            }
        }

//        boolean chatExists = false;
//        for (Chat c : chatWindow) {
//            if (c.getName().equals(m.getTarget())) {
//                chatExists = true;
//                c.addMessage(m);
//
//                // If it's a Private chat we can simply take the person from there
//                if (c instanceof PrivateChat) {
//                   m.setPerson(((PrivateChat) c).getPerson());
//                } else {
//                    // The message was sent in a chatroom, so we have to check if we have a person to associate with
//                    boolean personExists = false;
//                    for (Person p : persons) {
//                        if (p.getUsername().equals(m.getUsername())) {
//                            m.setPerson(p);
//                            personExists = true;
//                        }
//                    }
//                    if (!personExists) {
//                        Person newPerson = new Person(m.getUsername());
//                        persons.add(newPerson);
//                        m.setPerson(newPerson);
//                    }
//                }
//            }
//
//            // Must be a private chat, otherwise we would already have an object for the chatroom in chatWindow
//            if (!chatExists) {
//                Person newPerson = new Person(m.getUsername());
//                persons.add(newPerson);
//                newPerson.getChat().addMessage(m);
//            }
//        }
    }

    public ObservableList<Chat> getChats() {
        return chats;
    }

    public Chat getCurrentChat() {
        return currentChat;
    }

    public void setCurrentChat(Chat currentChat) {
        this.currentChat = currentChat;
    }
}
