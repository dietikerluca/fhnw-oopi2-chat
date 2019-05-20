package Main;

import java.util.ArrayList;

public class Chatroom {

    private String name;
    private ArrayList<Message> messages;

    public Chatroom(String name){
        this.name = name;
        this.messages = new ArrayList<Message>();
    }

    public void addMessage(Message message){
        this.messages.add(message);
    }

    public ArrayList<Message> getMessages(){
        return this.messages;
    }
}
