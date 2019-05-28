package Main;

import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;


public class ContactsWindow extends ScrollPane {
    ListView contactList;
    Messenger model;

    public ContactsWindow( Messenger messenger){
        this.model = messenger;
        contactList = new ListView<ContactCard>();
        contactList.prefWidthProperty().bind(this.widthProperty());
        contactList.prefHeightProperty().bind(this.heightProperty());
        this.setId("ContactsWindowScrollPane");
        this.contactList.setId("ContactsWindowList");
        this.setContent(contactList);
    }


    public void displayContact(Contact contact){
        contactList.getItems().add(new ContactCard(contact));
    }


    public Contact getSelectedContact() {
        ContactCard contactCard = (ContactCard) contactList.getSelectionModel().getSelectedItem();
        return contactCard.getContact();
    }

    public Contact getFocusedContact() {
        ContactCard contactCard = (ContactCard) contactList.getFocusModel().getFocusedItem();
        return contactCard.getContact();
    }
    public boolean isEmpty(){
        return contactList.getItems().isEmpty();
    }



}
