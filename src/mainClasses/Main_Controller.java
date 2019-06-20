package src.mainClasses;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.collections.ListChangeListener;
import src.ServiceLocator;
import src.commonClasses.ChatClient;
import src.commonClasses.Translator;
import src.commonViews.ChatroomsList;
import src.commonViews.ChoicePopUp;
import src.commonViews.ErrorPopUp;
import src.contactClasses.Contact_Controller;
import src.contactClasses.Contact_View;
import src.loginClasses.Login_Controller;
import src.loginClasses.Login_Model;
import src.loginClasses.Login_View;
import src.preferencesClasses.Preferences_Controller;
import src.preferencesClasses.Preferences_Model;
import src.preferencesClasses.Preferences_View;
import src.startChatClasses.*;
import src.typeClasses.Chat;
import src.typeClasses.Message;
import src.typeClasses.Person;
import src.typeClasses.PrivateChat;

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

    ServiceLocator sl;
    Translator tr;
    Logger logger;
    ChatClient chatClient;


    public Main_Controller(Main_Model model, Main_View view){
        this.model = model;
        this.view = view;

        sl = ServiceLocator.getServiceLocator();
        tr = sl.getTranslator();
        logger = sl.getLogger();
        chatClient = sl.getChatClient();

        // Listen for incoming messages
        chatClient.getIncomingMessages().addListener((ListChangeListener<Message>) c -> {
            while (c.next()) {
                for (Message m : c.getAddedSubList()) model.receiveMessage(m);
            }
        });

        view.mainMenu.joinChatRoom.setOnAction(e -> {
            ChatroomsList chatroomsList = new ChatroomsList();

            chatroomsList.chatroomsList.setOnMouseClicked(event -> {
                String roomName = chatroomsList.chatroomsList.getSelectionModel().getSelectedItem();
                chatroomsList.stop();
                model.joinChatroom(roomName);
            });
        });

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

        // Edit Person
        view.chatList.setOnContextMenuRequested(request -> {
            logger.fine("Context Menu Request discovered");
            if (view.chatList.isEmpty()){
                logger.info("No Contacts in the list");
            } else {
                Chat chat = view.chatList.getFocusedChat();
                logger.fine("Selected Chat: " + chat.getName());

                if (chat instanceof PrivateChat) {
                    Person person = ((PrivateChat) chat).getPerson();

                    Contact_View contactsWindow = new Contact_View(model, view, person);
                    new Contact_Controller(model, contactsWindow, view);
                } else {

                }
            }
        });
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
