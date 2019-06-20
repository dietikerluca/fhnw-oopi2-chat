package src.typeClasses;

public class PrivateChat extends Chat {
    private Person person;

    public PrivateChat(Person person) {
        super(person.getUsername());
    }

    public Person getPerson() {
        return person;
    }
}
