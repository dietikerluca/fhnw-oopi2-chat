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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public Person(String username, String firstname , String lastname) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.inContactList = true;
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

    public void setInContactList(boolean inContactList){
        this.inContactList = inContactList;
    }
}
