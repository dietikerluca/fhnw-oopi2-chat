package Main;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.KeyCode;

public class Controller {

    Messenger model;
    View view;
    Login_View login_view;
    Preferences preferencesView;
    Preferences_Controller prefController;


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
            preferencesView = new Preferences(model);
            prefController = new Preferences_Controller(preferencesView, model, view);
            preferencesView.start();
        });

        //New Message Entered and Sent
        view.interactionRibbon.sendBtn.setOnAction(event -> {
            //TODO
            String msg = view.interactionRibbon.messageField.getText();
            Message message = new Message(msg, false);
            if (view.contacts.contactList.getSelectionModel().getSelectedItem() == null){
                ErrorPopUp errorPopUp = new ErrorPopUp("Please select a contact first."); //TODO
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
                    ErrorPopUp errorPopUp = new ErrorPopUp("Please select a contact first."); //TODO
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

        //If selected Contact changes change messages displayed
        view.contacts.contactList.getSelectionModel().selectedItemProperty().addListener(change -> {
            view.updateChatsDisplayed(view.contacts.getSelectedContact());
        });



        //Create new Contact via Button
        view.mainMenu.createContact.setOnAction(event -> {
            //Create new Contact
        });





    }




}
