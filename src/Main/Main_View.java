package Main;

import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main_View {

    Stage stage;
    Main_Model model;
    ChatWindow chats;
    MainMenu mainMenu;
    ContactsWindow contacts;
    InteractionRibbon interactionRibbon;
    BorderPane chatInteractionContainer;


    public Main_View(Stage primaryStage, Main_Model model){
        this.stage = primaryStage;
        this.model = model;

        BorderPane root = new BorderPane();

        //New Menu
        mainMenu = new MainMenu();

        //New ChatWindow
        chats = new ChatWindow(this);
        interactionRibbon = new InteractionRibbon(model);
        chatInteractionContainer = new BorderPane();
        chatInteractionContainer.setBottom(interactionRibbon);
        chatInteractionContainer.setCenter(chats);

        //New Contacts Window
        contacts = new ContactsWindow(model);

        //Create SplitView
        SplitPane split = new SplitPane();
        split.getItems().addAll(contacts,  chatInteractionContainer);
        split.setDividerPositions(0.3f, 0.6f);

        //Add Items to Borderpane
        root.setTop(mainMenu);
        root.setCenter(split);


        //Stage Settings
        Scene scene = new Scene(root, 700, 500);
        stage.setTitle("Main_Model");
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(400);

        String stylesheet = getClass().getResource("stylesheet.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);

    }

    public void start(){
        stage.show();
    }

    public void stop() {
        stage.hide();
    }

    public void updateChatsDisplayed(Contact selectedContact) {
        chats.displayMessages(selectedContact.getMessages());
    }





}
