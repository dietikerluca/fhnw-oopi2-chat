package Main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class InteractionRibbon extends HBox {

    public TextField messageField;
    public Button sendBtn;
    private Messenger model;
    private ServiceLocator sl = ServiceLocator.getServiceLocator();
    private Translator tr = sl.getTranslator();

    public InteractionRibbon(Messenger model){
        this.model = model;
        this.setId("InteractionRibbonBox");

//        TODO Update Texts
//        Finish
        messageField = new TextField();
        sendBtn = new Button(tr.getString("interactionRibbon.messageSendBtn"));
        sendBtn.setId("SendButton");

        messageField.setPromptText(tr.getString("interactionRibbon.textField"));


        //Adds elemets to HBOX
        this.getChildren().addAll(messageField, sendBtn);
        this.setSpacing(20);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(5,0,5,0));

    }






}
