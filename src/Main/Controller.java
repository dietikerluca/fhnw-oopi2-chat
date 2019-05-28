package Main;


import javafx.scene.input.KeyCode;

public class Controller {

    Messenger model;
    View view;
    Login_View login_view;
    Preferences_View preferencesViewView;
    Preferences_Controller prefController;
    Translator tr = ServiceLocator.getServiceLocator().getTranslator();


    public Controller(Messenger model, View view){
        this.model = model;
        this.view = view;

        //TestMessageGenerator
        view.mainMenu.testReceived.setOnAction(event -> {
            Message msg = new Message("TestMessage Received", true);
            view.chats.displayNewMessage(msg);
        });

        //TestConntactGenerator
        view.mainMenu.testContact.setOnAction(event -> {
            Contact contact = new Contact();
            view.contacts.displayContact(contact);
        });

        //Show Login Window
        view.mainMenu.login.setOnAction(e -> {
                login_view = new Login_View(model);
                login_view.start();
        });

        //Show Settings
        view.mainMenu.preferences.setOnAction(e -> {
            preferencesViewView = new Preferences_View(model);
            prefController = new Preferences_Controller(preferencesViewView, model, view);
            preferencesViewView.start();
        });

        //New Message Entered and Sent
        view.interactionRibbon.sendBtn.setOnAction(event -> {
            //TODO
            String msg = view.interactionRibbon.messageField.getText();
            Message message = new Message(msg, false);
            if (view.contacts.contactList.getSelectionModel().getSelectedItem() == null){
                ErrorPopUp errorPopUp = new ErrorPopUp("Please select a contact first.", tr.getString("buttons.close")); //TODO
            } else {
                view.chats.displayNewMessage(new Message(msg, false));
                view.interactionRibbon.messageField.clear();
                ContactCard contactCard = (ContactCard) view.contacts.contactList.getSelectionModel().getSelectedItem();
                Contact respectiveContact = contactCard.getContact();
                respectiveContact.addMessage(message);
            }

        });

        view.interactionRibbon.messageField.setOnKeyPressed(event -> {
            //TODO
            if ( event.getCode() == KeyCode.ENTER){
                String msg = view.interactionRibbon.messageField.getText();
                Message message = new Message(msg, false);
                if (view.contacts.contactList.getSelectionModel().getSelectedItem() == null){
                    ErrorPopUp errorPopUp = new ErrorPopUp("Please select a contact first.", tr.getString("buttons.close")); //TODO
                } else {
                    view.chats.displayNewMessage(new Message(msg, false));
                    view.interactionRibbon.messageField.clear();
                    ContactCard contactCard = (ContactCard) view.contacts.contactList.getSelectionModel().getSelectedItem();
                    Contact respectiveContact = contactCard.getContact();
                    respectiveContact.addMessage(message);
                }

            }
            }
        );

        // Contact Creation
        view.mainMenu.createContact.setOnAction(event -> {
            System.out.println("OK");
            Contact_View cw = new Contact_View(this.model, this.view);
        });

        //If selected Contact changes change messages displayed
        view.contacts.contactList.getSelectionModel().selectedItemProperty().addListener(change -> {
            view.updateChatsDisplayed(view.contacts.getSelectedContact());
        });


        /*Contact Context Menu Request
        * ------------------------------------*/
        view.contacts.contactList.setOnContextMenuRequested(request -> {
            if (view.contacts.isEmpty()){
                //nothing
            } else {
                Contact contact = view.contacts.getFocusedContact();

                if (contact.getInContactList()){
                    //nothing
                } else {
                    ChoicePopUp choicePopUp = new ChoicePopUp(tr.getString("labels.contextContact"),
                            tr.getString("buttons.createContact"), tr.getString("buttons.close"),
                            tr.getString("windows.newContactChoice"));

                    choicePopUp.primaryBtn.setOnAction(action -> {
                        choicePopUp.stop();
                        Contact_View contactsWindow = new Contact_View(model, view, contact);
                        Contact_Controller conCon = new Contact_Controller(model, contactsWindow,view);
                    });
                }

            }

        });


    }




}
