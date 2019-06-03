package Main;

public class ChatroomMessage extends Message {

    private Contact contact;

    public ChatroomMessage(String msg, boolean received, Contact contact){
        super(msg, received);
        this.contact = contact;
    }


    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Contact getContact() {
        return contact;
    }
}
