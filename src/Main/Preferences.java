package Main;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Preferences {

    public Stage preferences;
    public Messenger model;
    public ChoiceBox<String> languages;
    private ServiceLocator sl = ServiceLocator.getServiceLocator();
    private Translator tr = sl.getTranslator();
    public Button saveSettingsBtn;
    public TextField ipAddressField, portField;
    public Label choiceLabel, ipAddressLbl, portLbl;

    public Preferences(Messenger modelAccess){
        preferences = new Stage();
        this.model = modelAccess;
        VBox vbox = new VBox();

        //Create Fields
        languages = new ChoiceBox<String>();
        choiceLabel = new Label(tr.getString("preferences.languagesLbl"));
        languages.getItems().addAll("English","Deutsch");
        languages.getSelectionModel().select(sl.getTranslator().getCurrentLocale().getDisplayLanguage());

        //Boxes for iP Address and Port Number
        HBox ipBox = new HBox();
        HBox portBox = new HBox();
        ipAddressLbl = new Label(tr.getString("preferences.ipAddressLbl"));
        ipAddressField = new TextField(tr.getString("preferences.ipAddressField"));
        portLbl = new Label(tr.getString("preferences.portLbl"));
        portField = new TextField(tr.getString("preferences.portField"));
        portField.setMaxWidth(30);
        ipBox.getChildren().addAll(ipAddressLbl, ipAddressField);
        portBox.getChildren().addAll(portLbl, portField);
        ipBox.setSpacing(10);
        portBox.setSpacing(10);

        ipBox.setPadding(new Insets(40,0,0,0));
        ipBox.setMaxWidth(300);
        portBox.setMaxWidth(300);
        portBox.setPadding(new Insets(0,0,40,0));

        saveSettingsBtn = new Button(tr.getString("preferences.saveBtn"));


        //Add fields to VBox
        vbox.getChildren().addAll(choiceLabel, languages, ipBox, portBox, saveSettingsBtn);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);

        //Create Scene
        Scene scene = new Scene(vbox, 400, 400);
        preferences.setMinHeight(350);
        preferences.setMinWidth(350);
        preferences.setTitle(tr.getString("windows.settings"));
        preferences.setScene(scene);

    }

    public void start(){
        preferences.show();
    }

    public void stop() {
        preferences.hide();
    }

    protected void updateTexts() {
//        The menu entries
        choiceLabel.setText(tr.getString("preferences.languagesLbl"));
        saveSettingsBtn.setText(tr.getString("preferences.saveBtn"));
        ipAddressLbl.setText(tr.getString("preferences.ipAddressLbl"));
        portLbl.setText(tr.getString("preferences.portLbl"));
    }


}
