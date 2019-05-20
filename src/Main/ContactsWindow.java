package Main;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;


public class ContactsWindow extends ScrollPane {
    ListView contactList;

    public ContactsWindow(){
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



}
