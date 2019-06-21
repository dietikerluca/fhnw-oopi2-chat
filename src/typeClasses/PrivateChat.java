package src.typeClasses;

public class PrivateChat extends Chat {
    private Person person;

    public PrivateChat(Person person, String target) {
        super(target);
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }
}
