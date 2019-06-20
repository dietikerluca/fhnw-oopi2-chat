package src.typeClasses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class Chat {
    ObservableList<Message> messages;
    String name;

    public Chat(String name) {
        messages = FXCollections.observableArrayList();
        this.name = name;
    }

    public void addMessage(Message m) {
        messages.add(m);
    }

    public String getName() {
        return name;
    }

    public ObservableList<Message> getMessages() {
        return messages;
    }
}
