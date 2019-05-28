package Main;

import java.util.ArrayList;

public class Messenger {
    ServiceLocator sl;
    Translator tr;
    ArrayList<Contact> contactList;

    public Messenger(){
        this.sl = ServiceLocator.getServiceLocator();
        this.tr = sl.getTranslator();

        // ContactList
        this.contactList = new ArrayList<>();

    }

    public void addContact(Contact contact){
        this.contactList.add(contact);
    }










//    public boolean login(String username, String password){
//
//    }
}
