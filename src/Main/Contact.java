package Main;

import java.util.ArrayList;

public class Contact {

    private String name, surname;
    private String username;
    private boolean inContactList;
    private ArrayList<Message> messages;

    public Contact(String prename, String surname){
        this.name = prename;
        this.surname = surname;
        this.messages = new ArrayList<Message>();
    }

    public Contact(){
        this.name = "Urs ";
        this.surname = "Schaeppi";
        this.messages = new ArrayList<Message>();
    }

    public String getPrename() {
        return name;
    }

    public String getSurname() {
        return surname;
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
}
