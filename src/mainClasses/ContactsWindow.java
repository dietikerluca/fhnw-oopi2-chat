package src.mainClasses;

import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import src.Main.ContactCard;
import src.ServiceLocator;

import java.util.ArrayList;
import java.util.logging.Logger;


public class ContactsWindow extends ScrollPane {
    ListView contactList;
    Main_Model model;
    ServiceLocator sl = ServiceLocator.getServiceLocator();
    Logger logger = sl.getLogger();

    public ContactsWindow( Main_Model mainModel){
        this.model = mainModel;
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
        return contactCard.getPerson();
    }

    public Contact getFocusedContact() {
        ContactCard contactCard = (ContactCard) contactList.getFocusModel().getFocusedItem();
        return contactCard.getPerson();
    }
    public boolean isEmpty(){
        return contactList.getItems().isEmpty();
    }

    public void updateContactCards(ArrayList<Contact> contacts){
        contactList.getItems().clear();
        logger.fine("Contact list cleared");
        logger.fine("Initializing new contacts.");
        for (Contact c : contacts){
            displayContact(c);
            logger.finest("Displaying Contact: "+c.getUsername());
        }
        logger.fine("Update accomplished.");

    }



}
