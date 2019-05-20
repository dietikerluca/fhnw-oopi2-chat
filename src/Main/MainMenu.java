package Main;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MainMenu extends MenuBar {

    Menu messenger, file, window, help;
    MenuItem login, logout, profile, preferences, quit, newChat, newChatRoom, joinChatRoom, zoom
            , newWindow, updates, guidance, testReceived, testContact, createContact;

    //Translator for Translating
    Translator t = ServiceLocator.getServiceLocator().getTranslator();

    public MainMenu(){
        messenger = new Menu();
        file = new Menu();
        window = new Menu();
        help = new Menu();

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

        zoom = new MenuItem("Set Zoom"); //TODO
        newWindow = new MenuItem("New Window"); //TODO

        window.getItems().addAll(zoom, newWindow);

        updates = new MenuItem(t.getString("mainmenu.WriteDeveloper"));
        guidance = new MenuItem(t.getString("mainmenu.Guidance"));

        help.getItems().addAll(updates, guidance);

        //Update texts
        updateTexts();

        //adds menues to menubar
        this.getMenus().addAll(messenger, file, window, help);

    }

    public void updateTexts() {
        Translator t = ServiceLocator.getServiceLocator().getTranslator();

//        The menu entries
        messenger.setText(t.getString("mainmenu.messenger"));
        file.setText(t.getString("mainmenu.file"));
        window.setText(t.getString("mainmenu.window"));
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

        zoom.setText("Set Zoom"); //TODO
        newWindow.setText("New Window"); //TODO

        updates.setText(t.getString("mainmenu.WriteDeveloper"));
        guidance.setText(t.getString("mainmenu.Guidance"));

    }
}
