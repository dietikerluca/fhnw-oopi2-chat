package src.mainClasses;

import src.ServiceLocator;
import src.commonClasses.Translator;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import src.commonViews.ImageLoader;

import java.io.File;
import java.util.logging.Logger;

public class MainMenu extends MenuBar {

    Menu messenger, file, help;
    MenuItem login, logout, profile, preferences, quit, newChat, newChatRoom, joinChatRoom, createContact
            , updates, guidance, testReceived, testContact, reportError;

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
        profile = new MenuItem(t.getString("mainmenu.Profile"));
        preferences = new MenuItem(t.getString("mainmenu.Preferences"));
        quit = new MenuItem(t.getString("mainmenu.Quit"));
        testReceived = new MenuItem("GenerateTestMessageReceived");
        testContact = new MenuItem("Generate Test Contact");

        messenger.getItems().addAll(login, logout, profile, preferences, quit);

        newChat = new MenuItem(t.getString("mainmenu.newChat"));
        newChatRoom = new MenuItem(t.getString("mainmenu.newChatRoom"));
        joinChatRoom = new MenuItem(t.getString("mainmenu.joinChatRoom"));
        createContact = new MenuItem(t.getString("mainmenu.createContact"));

        file.getItems().addAll(newChat, newChatRoom, joinChatRoom, createContact, testReceived, testContact);

        updates = new MenuItem(t.getString("mainmenu.WriteDeveloper")); //TODO
        guidance = new MenuItem(t.getString("mainmenu.Guidance")); //TODO
        reportError = new MenuItem(t.getString("mainmenu.reportError")); //TODO

        help.getItems().addAll(updates, guidance, reportError);

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
        profile.setText(t.getString("mainmenu.Profile"));
        preferences.setText(t.getString("mainmenu.Preferences"));
        quit.setText(t.getString("mainmenu.Quit"));
        testReceived.setText("GenerateTestMessageReceived");
        testContact.setText("Generate Test Contact");

        newChat.setText(t.getString("mainmenu.newChat"));
        newChatRoom.setText(t.getString("mainmenu.newChatRoom"));
        joinChatRoom.setText(t.getString("mainmenu.joinChatRoom"));
        createContact.setText(t.getString("mainmenu.createContact"));

        updates.setText(t.getString("mainmenu.WriteDeveloper"));
        guidance.setText(t.getString("mainmenu.Guidance"));

    }
}
