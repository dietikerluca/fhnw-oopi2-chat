package src.mainClasses;

import src.ServiceLocator;
import src.commonClasses.Translator;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class MainMenu extends MenuBar {

    Menu messenger, file, help;
    MenuItem login, logout, profile, preferences, quit, newChat, newChatRoom, joinChatRoom, createContact
            , updates, guidance, testReceived, testContact, reportError;
    ImageView chevronView, chevronView1, chevronView2, chevronView3;

    //Translator for Translating
    Translator t = ServiceLocator.getServiceLocator().getTranslator();

    public MainMenu(){

        /*Read icons
        * -------------------------------*/
        /*Load src.assets
         * ----------------------------------------*/
        try {
            String url = System.getProperty("user.dir");
            url += "/src/assets/img/chevron_down.png";
            File chevronFile = new File(url);
            url = chevronFile.toURI().toURL().toString();
            Image chevronDown = new Image(url);
            chevronView = new ImageView(chevronDown);
            chevronView.setFitHeight(20);
            chevronView.setFitWidth(20);
            chevronView1 = new ImageView(chevronDown);
            chevronView1.setFitHeight(20);
            chevronView1.setFitWidth(20);
            chevronView2 = new ImageView(chevronDown);
            chevronView2.setFitHeight(20);
            chevronView2.setFitWidth(20);
            chevronView3 = new ImageView(chevronDown);
            chevronView3.setFitHeight(20);
            chevronView3.setFitWidth(20);
        } catch (Exception e){
            e.printStackTrace();

        }

        messenger = new Menu("", chevronView);
        file = new Menu("", chevronView1);
        help = new Menu("", chevronView3);

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
