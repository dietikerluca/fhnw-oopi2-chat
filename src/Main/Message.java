package Main;

import java.time.LocalDateTime;

public class Message {

    private String message;
    private boolean received;
    private LocalDateTime messageTimeStamp;

    public Message(String msg, boolean received){
        this.message = msg;
        this.received = received;
        this.messageTimeStamp = LocalDateTime.now();
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }

    public String getMessage() {
        return message;
    }

    public boolean isReceived() {
        return received;
    }

    public LocalDateTime getMessageTimeStamp() {
        return messageTimeStamp;
    }
}
