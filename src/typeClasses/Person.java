package src.typeClasses;

import java.util.Date;

public class Person {
    private Date lastMessageReceived;

    private String username;
    private String firstname;
    private String lastname;
    private boolean inContactList;
    private boolean blocked;

    public Person(String username) {
        this.username = username;

        this.firstname = "";
        this.lastname = "";
        this.inContactList = false;
        this.blocked = false;
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
