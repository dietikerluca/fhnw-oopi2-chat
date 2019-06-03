package Main;

import java.util.ArrayList;

public class Chatroom {

    private String name;
    private ArrayList<ChatroomMessage> messages;

    public Chatroom(String name){
        this.name = name;
        this.messages = new ArrayList<>();
    }

    public void addMessage(ChatroomMessage message){
        this.messages.add(message);
    }

    public ArrayList<ChatroomMessage> getMessages(){
        return this.messages;
    }
}
