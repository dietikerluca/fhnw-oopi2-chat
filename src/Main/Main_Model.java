package Main;

import java.util.ArrayList;

public class Main_Model {
    ServiceLocator sl;
    Translator tr;
    ArrayList<Contact> contactList;

    public Main_Model(){
        this.sl = ServiceLocator.getServiceLocator();
        this.tr = sl.getTranslator();

        // ContactList
        this.contactList = new ArrayList<>();

    }

    public void addContact(Contact contact){
        this.contactList.add(contact);
    }

    public ArrayList<Contact> getContactList(){
        return this.contactList;
    }










//    public boolean login(String username, String password){
//
//    }
}
