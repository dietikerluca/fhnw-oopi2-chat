package Main;

public class Message {

    private String message;
    private boolean received;

    public Message(String msg, boolean received){
        this.message = msg;
        this.received = received;
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
}
