package src.startChatClasses;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import src.ServiceLocator;
import src.abstractClasses.View;
import src.commonClasses.Translator;
import src.mainClasses.Main_Model;

import java.util.logging.Logger;

public class StartChat_View extends View {

    TextField sendTo;
    Button startChat;


    public StartChat_View(Stage stage, Main_Model model) {
        super(stage, model);

        Translator tr = ServiceLocator.getServiceLocator().getTranslator();

        stage.setTitle(tr.getString("windows.startChat"));
        stage.setMaxHeight(300);
        stage.setMaxWidth(600);
        stage.setScene(create_GUI());
    }

    @Override
    protected Scene create_GUI() {

        ServiceLocator sl = ServiceLocator.getServiceLocator();
        Translator tr = sl.getTranslator();
        Logger logger = sl.getLogger();

        VBox root = new VBox();
        root.getStyleClass().add("windowPopUp");

        Label sendToLabel = new Label(tr.getString("labels.sendTo"));
        Label hintLabel = new Label(tr.getString("labels.sendToHint"));
        sendTo = new TextField();
        startChat = new Button(tr.getString("buttons.startChat"));
        root.isFocused();

        sendTo.setPromptText(tr.getString("startChatWindow.username"));

        sendToLabel.getStyleClass().add("header");
        hintLabel.getStyleClass().add("defaultLabel");
        startChat.getStyleClass().add("primary");

        root.getChildren().addAll(sendToLabel, hintLabel, sendTo, startChat);

        //TODO Empty Textfield Checker and Button disabler


        Scene scene = new Scene(root, 400, 300);
        String stylesheet = sl.getClass().getResource("stylesheet.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);

        return scene;
    }
}
