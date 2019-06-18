package src.typeClasses;

import java.util.Date;

public class Person {
    private PrivateChat chat;
    private Date lastMessageReceived;

    private String username;
    private String firstname;
    private String lastname;
    private boolean inContactList;
    private boolean blocked;

    public Person(String username) {
        this.username = username;

        this.chat = new PrivateChat(this);
    }

    public PrivateChat getChat() {
        return chat;
    }

    public Date getLastMessageReceived() {
        return lastMessageReceived;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public boolean isInContactList() {
        return inContactList;
    }

    public boolean isBlocked() {
        return blocked;
    }
}
