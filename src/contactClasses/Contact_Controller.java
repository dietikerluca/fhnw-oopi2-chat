package src.contactClasses;

import src.ServiceLocator;
import src.mainClasses.Main_Model;
import src.mainClasses.Main_View;
import src.typeClasses.Person;

import java.util.logging.Logger;

public class Contact_Controller {

    Main_Model model;
    Contact_View contactView;
    Main_View view;
    Logger logger = ServiceLocator.getServiceLocator().getLogger();

    public Contact_Controller(Main_Model mainModel, Contact_View contact_view, Main_View view){
        this.model = mainModel;
        this.contactView = contact_view;
        this.view = view;


       //Listens for Button Clicks in Contact Main_View
        contactView.saveBtn.setOnAction(event -> {
            logger.fine("Button 'Save' clicked");
            if(contact_view.getExisting() == null) {
                logger.fine("Contact Creation from Scratch");
                Person contact = new Person(contactView.username.getText(),
                        contactView.firstName.getText(),
                        contactView.lastName.getText());

                mainModel.addPerson(contact);
                mainModel.createPrivateChat(contact.getUsername());
                logger.fine("Contact added to contactslist");
                contact_view.stop();
                view.chatList.updateChatList();

            } else {
                logger.fine("Contact Creation from existing person");
                //TODO Change Person
                contact_view.getExisting().setInContactList(true);
                contact_view.getExisting().setFirstname(contact_view.firstName.getText());
                contact_view.getExisting().setLastname(contact_view.lastName.getText());
                contact_view.getExisting().setUsername(contact_view.username.getText());
                contact_view.getExisting().setBlocked(contact_view.blocked.isSelected());
                contact_view.stop();
                view.chatList.updateChatList();
            }
        });

    }
}
