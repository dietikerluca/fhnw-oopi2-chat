package src.contactClasses;

import src.ServiceLocator;
import src.mainClasses.Main_Model;
import src.mainClasses.Main_View;

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
//        contactView.saveBtn.setOnAction(event -> {
//            logger.fine("Button 'Save' clicked");
//            if(contact_view.getExisting() == null) {
//                logger.fine("Contact Creation from Scratch");
//                Contact contact = new Contact(contactView.firstName.getText(),
//                        contactView.lastName.getText(), contactView.username.getText(), true);
//                model.addContact(contact);
//                logger.fine("Contact added to contactslist");
//                view.contacts.displayContact(contact);
//                contact_view.stop();
//                /* TODO
//                 *   eventuell sollten wir uns überlegen statt überall methoden aufzurufen ein bindinng zu einer
//                 * observ. List zu machen*/
//            } else {
//                logger.fine("Contact Creation from existing person");
//                contact_view.getExisting().setInContactList(true);
//                contact_view.getExisting().setName(contact_view.firstName.getText());
//                contact_view.getExisting().setLastname(contact_view.lastName.getText());
//                contact_view.getExisting().setUsername(contact_view.username.getText());
//                contact_view.getExisting().setBlocked(contact_view.blocked.isSelected());
//                view.contacts.updateContactCards(model.getContactList());
//                contact_view.stop();
//            }
//        });

    }
}