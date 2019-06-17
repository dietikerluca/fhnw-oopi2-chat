package Main;


import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

public class Main_Controller {

    Main_Model model;
    Main_View view;
    Login_View login_view;

    Preferences_Model preferences_model;
    Preferences_View preferences_view;
    Preferences_Controller prefController;

    ServiceLocator sl = ServiceLocator.getServiceLocator();
    Translator tr = sl.getTranslator();
    Logger logger = sl.getLogger();


    public Main_Controller(Main_Model model, Main_View view){
        this.model = model;
        this.view = view;

        //TestMessageGenerator
        view.mainMenu.testReceived.setOnAction(event -> {
            logger.fine("Button: Test Message Received");
            Message msg = new Message("TestMessage Received", true);
            view.chats.displayNewMessage(msg);
        });

        //TestConntactGenerator
        view.mainMenu.testContact.setOnAction(event -> {
            logger.fine("Button: Test Contact");
            Contact contact = new Contact();
            view.contacts.displayContact(contact);
        });

        //Show Login Window
        view.mainMenu.login.setOnAction(e -> {
            logger.fine("Button: Login");
            Stage loginStage = new Stage();
            Login_Model login_model = new Login_Model();
            login_view = new Login_View(loginStage, login_model);
            Login_Controller login_controller = new Login_Controller(login_model, login_view);
                login_view.start();

        });

        //Show Settings
        view.mainMenu.preferences.setOnAction(e -> {
            logger.fine("Button: Preferences");
            preferences_model = new Preferences_Model();

            Stage prefStage = new Stage();
            preferences_view = new Preferences_View(prefStage, preferences_model);
            prefController = new Preferences_Controller(preferences_view, preferences_model, view);
            preferences_view.start();
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
            logger.fine("Button: Create Contact");
            System.out.println("OK");
            Contact_View cw = new Contact_View(this.model, this.view);
        });

        //If selected Contact changes change messages displayed
        view.contacts.contactList.getSelectionModel().selectedItemProperty().addListener(change -> {
            try {
                logger.fine("New selected Contact: " + view.contacts.getSelectedContact().getPrename() + " " +
                        view.contacts.getSelectedContact().getLastname());
                view.updateChatsDisplayed(view.contacts.getSelectedContact());
            } catch (Exception e){
                logger.warning("No Contact found.");
            }
        });

        /*Help Menu
        * ------------------------------------*/

        view.mainMenu.reportError.setOnAction(event -> {
            logger.finest("User tries to report error.");

            ChoicePopUp choicePopUp = new ChoicePopUp(tr.getString("labels.includeLogFiles"),
                    tr.getString("buttons.ok"),
                    tr.getString("buttons.close"),
                    tr.getString("windows.includeLogFiles"));

            Desktop desktop;
            if (Desktop.isDesktopSupported()
                    && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
                URI mailto = null;
                try {
                    mailto = new URI("mailto:luca.dietiker@students.fhnw.ch?subject=Error%20Reporting");
                } catch (URISyntaxException e) {
                    logger.warning("Mail error: "+e.getStackTrace().toString());
                }
                try {
                    desktop.mail(mailto);
                } catch (IOException e) {
                    logger.warning("Mail error: "+e.getStackTrace().toString());
                }
            } else {

                logger.warning("Mail error Desktop not supported");
            }
        });


        /*Contact Context Menu Request
        * ------------------------------------*/
        view.contacts.contactList.setOnContextMenuRequested(request -> {
            logger.fine("Context Menu Request discovered");
            if (view.contacts.isEmpty()){
                logger.info("No Contacts in the list");
            } else {
                Contact contact = view.contacts.getFocusedContact();
                logger.fine("Selected Contact: "+contact.getPrename());
                if (!contact.getInContactList()){
                    logger.info(contact.getPrename()+" not in contact list.");
                    ChoicePopUp choicePopUp = new ChoicePopUp(tr.getString("labels.contextContact"),
                            tr.getString("buttons.createContact"),
                            tr.getString("buttons.close"),
                            tr.getString("windows.newContactChoice"));

                    choicePopUp.primaryBtn.setOnAction(action -> {
                        choicePopUp.stop();
                        Contact_View contactsWindow = new Contact_View(model, view, contact);
                        Contact_Controller conCon = new Contact_Controller(model, contactsWindow, view);
                    });

                } else {
                    logger.info(contact.getPrename()+" exists in contact list already.");
                    ChoicePopUp choicePopUp = new ChoicePopUp(tr.getString("labels.contextContactExisting"),
                            tr.getString("buttons.edit"),
                            tr.getString("buttons.close"),
                            tr.getString("windows.editContact"));

                    choicePopUp.primaryBtn.setOnAction(action -> {
                        choicePopUp.stop();
                        Contact_View contactsWindow = new Contact_View(model, view, contact);
                        Contact_Controller conCon = new Contact_Controller(model, contactsWindow, view);
                    });



            }

        }
        });


    }




}
