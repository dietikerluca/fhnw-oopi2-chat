package src.mainClasses;

import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import src.ServiceLocator;

public class Main_View {
    Stage stage;
    Main_Model model;
    ChatWindow chatWindow;
    MainMenu mainMenu;
    public ChatList chatList;
    InteractionRibbon interactionRibbon;
    BorderPane chatInteractionContainer;
    ServiceLocator sl;

    public Main_View(Stage primaryStage, Main_Model model){
        this.stage = primaryStage;
        this.model = model;
        sl = ServiceLocator.getServiceLocator();

        BorderPane root = new BorderPane();

        // Create Main Menu
        mainMenu = new MainMenu();

        // New ChatWindow
        chatWindow = new ChatWindow(model);
        interactionRibbon = new InteractionRibbon(model);

        chatInteractionContainer = new BorderPane();
        chatInteractionContainer.setBottom(interactionRibbon);
        chatInteractionContainer.setCenter(chatWindow);

        // New Chat List
        chatList = new ChatList(model);

        // Create Split Pane
        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(chatList,  chatInteractionContainer);
        splitPane.setDividerPositions(0.3f, 0.6f);

        // Add Items to BorderPane
        root.setTop(mainMenu);
        root.setCenter(splitPane);

        // Stage Settings
        Scene scene = new Scene(root, 700, 500);
        stage.setTitle("Main_Model");
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(400);

        String stylesheet = sl.getClass().getResource("stylesheet.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);
    }

    public void start(){
        stage.show();
    }

    public void stop() {
        stage.hide();
    }

    // public void updateChatsDisplayed(Contact selectedContact) {
    //    chatWindow.displayMessages(selectedContact.getMessages());
    //}
}
