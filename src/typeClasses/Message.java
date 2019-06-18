package src.typeClasses;

import java.time.LocalDateTime;

public class Message {
    private String message;
    private String username;
    private Person person;

    private boolean received;
    private LocalDateTime messageTimeStamp;

    public Message(String username, String target, String message, boolean received){
        this.username = username;
        this.message = message;
        this.received = received;
        this.messageTimeStamp = LocalDateTime.now();

        // TODO find Person
    }

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }

    public Person getPerson() {
        return person;
    }

    public boolean isReceived() {
        return received;
    }

    public LocalDateTime getMessageTimeStamp() {
        return messageTimeStamp;
    }

    @Override
    public String toString() {
        return "Message from " + username + ": " + message + ", received " + messageTimeStamp.toString();
    }
}
