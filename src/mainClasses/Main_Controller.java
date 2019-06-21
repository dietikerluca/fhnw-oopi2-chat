package src.mainClasses;

import javafx.collections.ListChangeListener;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import src.ServiceLocator;
import src.accountClasses.ChangePassword_Controller;
import src.accountClasses.ChangePassword_Model;
import src.accountClasses.ChangePassword_View;
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

        // Join a Chatroom
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

        // Logout
        view.mainMenu.logout.setOnAction(event -> {
            ChoicePopUp choicePopUp = new ChoicePopUp(tr.getString("labels.logout"),
                    tr.getString("buttons.logout"), tr.getString("buttons.back"),
                    tr.getString("windows.logout"), true);
            choicePopUp.primaryBtn.setOnAction(deleteEvent -> {
                chatClient.logout();
                choicePopUp.stop();
                logger.fine("Userchoice: Logout - Request sent");
                ChoicePopUp deleted = new ChoicePopUp(tr.getString("labels.loggedOut"),
                        tr.getString("buttons.close"), tr.getString("windows.loggedOut"));
                deleted.secondaryBtn.setOnAction(close -> {
                    view.stop();
                    deleted.stop();
                });
            });
            choicePopUp.secondaryBtn.setOnAction(cancelEvent -> {
                logger.fine("Usechoice: Stop logging out");
            });
        });

        // Change Password
        view.mainMenu.changePassword.setOnAction(event -> {
            Stage passwordStage = new Stage();
            ChangePassword_Model changePassword_model = new ChangePassword_Model();
            ChangePassword_View changePassword_view = new ChangePassword_View(passwordStage, changePassword_model);
            ChangePassword_Controller changePassword_controller = new ChangePassword_Controller(changePassword_model,
                    changePassword_view);
            changePassword_view.start();
        });

        // Delete Account
        view.mainMenu.deleteAccount.setOnAction(event -> {
            ChoicePopUp choicePopUp = new ChoicePopUp(tr.getString("labels.deleteAccount"),
                    tr.getString("buttons.delete"), tr.getString("buttons.back"),
                    tr.getString("windows.deleteAccount"), true);
            choicePopUp.primaryBtn.setOnAction(deleteEvent -> {
                chatClient.deleteAccount();
                logger.fine("Userchoice: Delete Account - Request sent");
                ChoicePopUp deleted = new ChoicePopUp(tr.getString("labels.accountDeleted"),
                        tr.getString("buttons.close"), tr.getString("windows.accountDeleted"));
                choicePopUp.stop();
            });
            choicePopUp.secondaryBtn.setOnAction(cancelEvent -> {
                logger.fine("Usechoice: Stop deleting Account");
                choicePopUp.stop();
            });
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

        view.mainMenu.quit.setOnAction(closeRequest -> {
            ChoicePopUp closePopUp = new ChoicePopUp(tr.getString("labels.closeApp"),
                    tr.getString("buttons.close"),
                    tr.getString("buttons.back"),
                    tr.getString("windows.closeRequest"),
                    true);
            closePopUp.primaryBtn.setOnAction(closeChoice -> {
                closePopUp.stop();
                view.stop();
                logger.fine("Userchoice: Close.");
            });
            closePopUp.secondaryBtn.setOnAction(goBack -> {
                closePopUp.stop();
                logger.fine("Userchoice: Go Back.");
            });

        });

        // Send message
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
            StartChat_View startChat_view = new StartChat_View(startChat_Stage, model);
            new StartChat_Controller(model, startChat_view);
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

        view.mainMenu.createContact.setOnAction(createEvent -> {
            Contact_View contact_view = new Contact_View(model, view);
            Contact_Controller contact_controller = new Contact_Controller(model, contact_view, view);
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
            ErrorPopUp errorPopUp = new ErrorPopUp("Please select a chat first.", tr.getString("buttons.close"));
        }
    }
}
