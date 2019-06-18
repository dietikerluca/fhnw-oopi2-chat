package src.typeClasses;

import javafx.collections.ObservableList;

public abstract class Chat {
    ObservableList<Message> messages;

    public Chat() {

    }

    public void addMessage(Message m) {
        messages.add(m);
    }
}
