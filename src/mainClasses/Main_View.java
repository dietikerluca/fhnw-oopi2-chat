package src.mainClasses;

import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import src.ServiceLocator;
import src.abstractClasses.View;

public class Main_View extends View<Main_Model> {
    ChatWindow chatWindow;
    public MainMenu mainMenu;
    public ChatList chatList;
    InteractionRibbon interactionRibbon;
    BorderPane chatInteractionContainer;
    ServiceLocator sl;

    public Main_View(Stage primaryStage, Main_Model model){
        super(primaryStage, model);

        stage.setTitle("Messenger");
        stage.setMinWidth(800);
        stage.setMinHeight(400);
    }

    public Scene create_GUI() {
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

        String stylesheet = sl.getClass().getResource("stylesheet.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);

        return scene;
    }

    public void start(){
        stage.show();
    }

    public void stop() {
        stage.hide();
    }
}
