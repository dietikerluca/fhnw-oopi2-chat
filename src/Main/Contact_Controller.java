package Main;

public class Contact_Controller {

    Messenger model;
    Contact_View contactView;
    View view;

    public Contact_Controller(Messenger messenger, Contact_View contact_view, View view){
        this.model = messenger;
        this.contactView = contact_view;
        this.view = view;


       //Listens for Button Clicks in Contact View
        contactView.saveBtn.setOnAction(event -> {
            if(contact_view.getExisting() == null) {
                Contact contact = new Contact(contactView.firstName.getText(),
                        contactView.lastName.getText(), contactView.username.getText(), true);
                model.addContact(contact);
                view.contacts.displayContact(contact);
                contact_view.stop();
                /* TODO
                 *   eventuell sollten wir uns überlegen statt überall methoden aufzurufen ein bindinng zu einer
                 * observ. List zu machen*/
            } else {
                contact_view.getExisting().setInContactList(true);
                contact_view.getExisting().setName(contact_view.firstName.getText());
                contact_view.getExisting().setLastname(contact_view.lastName.getText());
                contact_view.getExisting().setUsername(contact_view.username.getText());
                contact_view.stop();
            }
        });


        /*Context Menu Request for Contact in List
        * ---------------------------------------------*/






    }
}
