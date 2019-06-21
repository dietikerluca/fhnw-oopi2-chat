package src.mainClasses;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import src.ServiceLocator;
import src.commonClasses.Translator;
import src.commonViews.ImageLoader;

import java.util.logging.Logger;

public class MainMenu extends MenuBar {

    Menu messenger, file, help;
    MenuItem login, logout, changePassword, deleteAccount, preferences, quit, newChat, newChatRoom, joinChatRoom, createContact
            , guidance, reportError;

    Image chevronImage;

    ServiceLocator serviceLocator;
    Translator t;
    Logger logger;

    public MainMenu(){
        serviceLocator = ServiceLocator.getServiceLocator();
        t = serviceLocator.getTranslator();
        logger = serviceLocator.getLogger();

        // Load Chevron Icon
        try {
            chevronImage = ImageLoader.loadImage("/src/assets/img/chevron_down.png");
        } catch (Exception e){
            logger.warning("image could not be loaded. \n Stack Trace: " + e);
        }

        ImageView messengerChevron = new ImageView(chevronImage);
        messengerChevron.setFitHeight(20);
        messengerChevron.setFitWidth(20);

        ImageView fileChevron = new ImageView(chevronImage);
        fileChevron.setFitHeight(20);
        fileChevron.setFitWidth(20);

        ImageView helpChevron = new ImageView(chevronImage);
        helpChevron.setFitHeight(20);
        helpChevron.setFitWidth(20);

        messenger = new Menu("", messengerChevron);
        file = new Menu("", fileChevron);
        help = new Menu("", helpChevron);

        login = new MenuItem(t.getString("mainmenu.Login"));
        logout = new MenuItem(t.getString("mainmenu.Logout"));
        changePassword = new MenuItem(t.getString("mainmenu.changePassword"));
        deleteAccount = new MenuItem(t.getString("mainmenu.deleteAccount"));
        preferences = new MenuItem(t.getString("mainmenu.Preferences"));
        quit = new MenuItem(t.getString("mainmenu.Quit"));
        testReceived = new MenuItem("GenerateTestMessageReceived");
        testContact = new MenuItem("Generate Test Contact");

        messenger.getItems().addAll(login, logout, changePassword, deleteAccount, preferences, quit);

        newChat = new MenuItem(t.getString("mainmenu.newChat"));
        newChatRoom = new MenuItem(t.getString("mainmenu.newChatRoom"));
        joinChatRoom = new MenuItem(t.getString("mainmenu.joinChatRoom"));
        createContact = new MenuItem(t.getString("mainmenu.createContact"));

        file.getItems().addAll(newChat, newChatRoom, joinChatRoom, createContact);

        guidance = new MenuItem(t.getString("mainmenu.Guidance")); //TODO
        reportError = new MenuItem(t.getString("mainmenu.reportError")); //TODO

        help.getItems().addAll(guidance, reportError);

        //Update texts
        updateTexts();

        //adds menues to menubar
        this.getMenus().addAll(messenger, file, help);

    }

    public void updateTexts() {
        Translator t = ServiceLocator.getServiceLocator().getTranslator();

//        The menu entries
        messenger.setText(t.getString("mainmenu.messenger"));
        file.setText(t.getString("mainmenu.file"));
        help.setText(t.getString("mainmenu.help"));

        login.setText(t.getString("mainmenu.Login"));
        logout.setText(t.getString("mainmenu.Logout"));
        changePassword.setText(t.getString("mainmenu.changePassword"));
        deleteAccount.setText(t.getString("mainmenu.deleteAccount"));
        preferences.setText(t.getString("mainmenu.Preferences"));
        quit.setText(t.getString("mainmenu.Quit"));

        newChat.setText(t.getString("mainmenu.newChat"));
        newChatRoom.setText(t.getString("mainmenu.newChatRoom"));
        joinChatRoom.setText(t.getString("mainmenu.joinChatRoom"));
        createContact.setText(t.getString("mainmenu.createContact"));

        guidance.setText(t.getString("mainmenu.Guidance"));

    }
}
