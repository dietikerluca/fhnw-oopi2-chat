package src.startChatClasses;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import src.ServiceLocator;
import src.abstractClasses.Model;
import src.abstractClasses.View;
import src.commonClasses.Translator;

import java.util.logging.Logger;

public class CreateChatroom_View extends View {

    public TextField chatroomField;
    public Button createButton;
    /**
     * Set any options for the stage in the subclass constructor
     *
     * @param stage
     * @param model
     */
    public CreateChatroom_View(Stage stage, Model model) {
        super(stage, model);
    }

    @Override
    protected Scene create_GUI() {
        ServiceLocator sl = ServiceLocator.getServiceLocator();
        Translator tr = sl.getTranslator();
        Logger logger = sl.getLogger();

        VBox root = new VBox();
        root.getStyleClass().add("windowPopUp");

        Label newChatroom = new Label(tr.getString("labels.newChatroom"));
        Label hintChatroomLabel = new Label(tr.getString("labels.newChatroomHint"));
        chatroomField = new TextField();
        createButton = new Button(tr.getString("buttons.create"));

        newChatroom.getStyleClass().add("header");
        hintChatroomLabel.getStyleClass().add("defaultLabel");
        createButton.getStyleClass().add("primary");

        root.getChildren().addAll(newChatroom, hintChatroomLabel, chatroomField, createButton);

        Scene scene = new Scene(root, 400, 300);
        String stylesheet = sl.getClass().getResource("stylesheet.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);

        return scene;
    }
}
