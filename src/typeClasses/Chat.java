package src.typeClasses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class Chat {
    ObservableList<Message> messages;

    public Chat() {
        messages = FXCollections.observableArrayList();
    }

    public void addMessage(Message m) {
        messages.add(m);
    }
}
