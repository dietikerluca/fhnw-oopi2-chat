package src.mainClasses;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.collections.ListChangeListener;
import javafx.stage.Stage;
import src.ServiceLocator;
import src.commonClasses.ChatClient;
import src.commonClasses.Translator;
import src.commonViews.ChoicePopUp;
import src.commonViews.ErrorPopUp;
import src.loginClasses.Login_Controller;
import src.loginClasses.Login_Model;
import src.loginClasses.Login_View;
import src.preferencesClasses.Preferences_Controller;
import src.preferencesClasses.Preferences_Model;
import src.preferencesClasses.Preferences_View;
import src.startChatClasses.*;
import src.typeClasses.Chat;
import src.typeClasses.Message;

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

        ChatClient chatClient = sl.getChatClient();

        chatClient.getIncomingMessages().addListener((ListChangeListener<Message>) c -> {
            while (c.next()) {
                for (Message m : c.getAddedSubList()) model.receiveMessage(m);
            }
        });

        view.mainMenu.joinChatRoom.setOnAction(e -> model.joinChatroom("CatChat"));

        // Show Login Window
        view.mainMenu.login.setOnAction(e -> {
            logger.fine("Button: Login");
            Stage loginStage = new Stage();
            Login_Model login_model = new Login_Model();
            login_view = new Login_View(loginStage, login_model);
            new Login_Controller(login_model, login_view);
            login_view.start();
        });

        // Show Settings
        view.mainMenu.preferences.setOnAction(e -> {
            logger.fine("Button: Preferences");
            preferences_model = new Preferences_Model();

            Stage prefStage = new Stage();
            preferences_view = new Preferences_View(prefStage, preferences_model);
            prefController = new Preferences_Controller(preferences_view, preferences_model, view);
            preferences_view.start();
        });

        // New Message Entered and Sent
        view.interactionRibbon.sendBtn.setOnAction(event -> sendMessageAction());
        view.interactionRibbon.messageField.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) sendMessageAction();
            }
        );

        // Contact Creation
//        view.mainMenu.createContact.setOnAction(event -> {
//            logger.fine("Button: Create Contact");
//            System.out.println("OK");
//            Contact_View cw = new Contact_View(this.model, this.view);
//        });

        view.chatList.getChatList().getSelectionModel().selectedItemProperty().addListener(change -> {
            model.setCurrentChat(view.chatList.getSelectedChat());
            model.getCurrentChat().getMessages().addListener((ListChangeListener<Message>) c -> view.chatWindow.updateChatWindow());
            view.chatWindow.updateChatWindow();
        });

        // Report Error
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
                    logger.warning("Mail error: " + e.getStackTrace().toString());
                }
                try {
                    desktop.mail(mailto);
                } catch (IOException e) {
                    logger.warning("Mail error: " + e.getStackTrace().toString());
                }
            } else {
                logger.warning("Mail error Desktop not supported");
            }
        });

        view.mainMenu.newChat.setOnAction(event -> {
            Stage startChat_Stage = new Stage();
            StartChat_Model startChat_model = new StartChat_Model();
            StartChat_View startChat_view = new StartChat_View(startChat_Stage, startChat_model);
            StartChat_Controller startChat_controller = new StartChat_Controller(startChat_model, startChat_view);
            startChat_view.start();
        });

        view.mainMenu.newChatRoom.setOnAction(event -> {
            Stage createChatroom_Stage = new Stage();
            CreateChatroom_Model createChatroom_model = new CreateChatroom_Model();
            CreateChatroom_View createChatroom_view = new CreateChatroom_View(createChatroom_Stage, createChatroom_model);
            CreateChatroom_Controller createChatroom_controller = new CreateChatroom_Controller(
                    createChatroom_model, createChatroom_view);
            createChatroom_view.start();
        });


        /*Contact Context Menu Request
        * ------------------------------------*/
//        view.contacts.chatList.setOnContextMenuRequested(request -> {
//            logger.fine("Context Menu Request discovered");
//            if (view.contacts.isEmpty()){
//                logger.info("No Contacts in the list");
//            } else {
//                Contact contact = view.contacts.getFocusedContact();
//                logger.fine("Selected Contact: "+contact.getPrename());
//                if (!contact.getInContactList()){
//                    logger.info(contact.getPrename()+" not in contact list.");
//                    ChoicePopUp choicePopUp = new ChoicePopUp(tr.getString("labels.contextContact"),
//                            tr.getString("buttons.createContact"),
//                            tr.getString("buttons.close"),
//                            tr.getString("windows.newContactChoice"));
//
//                    choicePopUp.primaryBtn.setOnAction(action -> {
//                        choicePopUp.stop();
//                        Contact_View contactsWindow = new Contact_View(model, view, contact);
//                        Contact_Controller conCon = new Contact_Controller(model, contactsWindow, view);
//                    });
//
//                } else {
//                    logger.info(contact.getPrename()+" exists in contact list already.");
//                    ChoicePopUp choicePopUp = new ChoicePopUp(tr.getString("labels.contextContactExisting"),
//                            tr.getString("buttons.edit"),
//                            tr.getString("buttons.close"),
//                            tr.getString("windows.editContact"));
//
//                    choicePopUp.primaryBtn.setOnAction(action -> {
//                        choicePopUp.stop();
//                        Contact_View contactsWindow = new Contact_View(model, view, contact);
//                        Contact_Controller conCon = new Contact_Controller(model, contactsWindow, view);
//                    });
//
//
//
//            }
//
//        }
//        });
    }

    void sendMessageAction () {
        String msg = view.interactionRibbon.messageField.getText();
        view.interactionRibbon.messageField.clear();

        Chat currentChat = model.getCurrentChat();

        if (currentChat != null) {
            model.sendMessage(currentChat, msg);
        } else {
            ErrorPopUp errorPopUp = new ErrorPopUp("Please select a contact first.", tr.getString("buttons.close"));
        }
    }
}
