package Main;

import java.util.ArrayList;
import java.util.logging.Logger;

public class Contact {

    private String name, lastname, nickname;
    private String username;
    private boolean inContactList, blocked;
    private ArrayList<Message> messages;
    Logger logger = ServiceLocator.getServiceLocator().getLogger();


    //Constructor with username
    public Contact(String prename, String lastname, String username, boolean inContactList){
        this.name = prename;
        this.lastname = lastname;
        this.username = username;
        this.inContactList = inContactList;
        this.messages = new ArrayList<Message>();
        this.blocked = false;
        logger.fine("Contact created via fullConstructor");

    }

    //Constructor without username
    public Contact(String prename, String lastname, boolean inContactList){
        this.name = prename;
        this.lastname = lastname;
        this.inContactList = inContactList;
        this.messages = new ArrayList<Message>();
        this.blocked = false;
        logger.fine("Contact created via partConstructor");
    }

    public Contact(){
        this.name = "Urs ";
        this.lastname = "Schaeppi";
        this.messages = new ArrayList<Message>();
        this.blocked = false;
        logger.fine("Contact created via DefaultConstructor");
    }

    public String getPrename() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setInContactList(boolean contained){
        this.inContactList = contained;
    }

    public boolean getInContactList(){
        return this.inContactList;
    }

    public void addMessage(Message msg){
        this.messages.add(msg);
    }

    public ArrayList<Message> getMessages(){
        return messages;
    }

    public String getUsername(){
        return this.username;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public boolean setBlocked(boolean blocked){
        return this.blocked = blocked;
    }


}
