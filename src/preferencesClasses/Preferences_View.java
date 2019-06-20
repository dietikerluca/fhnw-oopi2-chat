package src.preferencesClasses;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import src.ServiceLocator;
import src.abstractClasses.View;
import src.commonClasses.Translator;
import src.mainClasses.Main_Model;

import java.io.File;
import java.util.logging.Logger;

public class Preferences_View extends View {

    private ServiceLocator sl;
    private Translator tr;
    private Logger logger;

    public Main_Model model;
    public ChoiceBox<String> languages;

    public Button saveSettingsBtn, savedButton;
    public TextField ipAddressField, portField;
    public Label choiceLabel, ipAddressLbl, portLbl, header;
    public boolean buttonPressed = false;
    public HBox buttonBox;

    public Preferences_View(Stage primaryStage, Preferences_Model model){
        super(primaryStage, model);

        stage.setAlwaysOnTop(true);
        stage.setMinHeight(450);
        stage.setMinWidth(450);
        stage.setTitle(tr.getString("windows.settings"));

        ServiceLocator.getServiceLocator().getLogger().info("Settings initialized.");
    }


    public Scene create_GUI(){
        sl = ServiceLocator.getServiceLocator();
        tr = sl.getTranslator();
        logger = sl.getLogger();

        //Create Root Node
        VBox root = new VBox();
        root.getStyleClass().add("windowPopUp");

        //Create Fields
        header = new Label(tr.getString("header.settings"));
        header.getStyleClass().add("header");
        languages = new ChoiceBox<String>();
        choiceLabel = new Label(tr.getString("preferences.languagesLbl"));
        languages.getItems().addAll("English","Deutsch"); //TODO von SL holen
        languages.getSelectionModel().select(sl.getTranslator().getCurrentLocale().getDisplayLanguage());

        //Boxes for iP Address and Port Number
        HBox languagesBox = new HBox();
        HBox ipBox = new HBox();
        HBox portBox = new HBox();
        buttonBox = new HBox();
        languagesBox.setAlignment(Pos.CENTER_LEFT);
        ipBox.setAlignment(Pos.CENTER_LEFT);
        portBox.setAlignment(Pos.CENTER_LEFT);
        buttonBox.setAlignment(Pos.CENTER);

        //Labels and Fields
        ipAddressLbl = new Label(tr.getString("preferences.ipAddressLbl"));
        ipAddressField = new TextField(tr.getString("preferences.ipAddressField"));
        portLbl = new Label(tr.getString("preferences.portLbl"));
        portField = new TextField(tr.getString("preferences.portField"));
        portField.setMaxWidth(150);

        //Set Prompt Texts
        ipAddressField.setPromptText(sl.getIpAddressPreset());
        portField.setPromptText(String.valueOf(sl.getPort()));

        //Apply Styling to Labels
        choiceLabel.getStyleClass().add("defaultLabel");
        ipAddressLbl.getStyleClass().add("defaultLabel");
        portLbl.getStyleClass().add("defaultLabel");
        choiceLabel.setMinWidth(170);
        ipAddressLbl.setMinWidth(170);
        portLbl.setMinWidth(170);

        //Add Button
        saveSettingsBtn = new Button(tr.getString("preferences.saveBtn"));
        saveSettingsBtn.getStyleClass().add("confirm");
        saveSettingsBtn.setAlignment(Pos.CENTER);


        //Add Fields an Buttons to Boxes
        languagesBox.getChildren().addAll(choiceLabel, languages);
        ipBox.getChildren().addAll(ipAddressLbl, ipAddressField);
        portBox.getChildren().addAll(portLbl, portField);
        buttonBox.getChildren().add(saveSettingsBtn);
        languagesBox.setSpacing(10);
        ipBox.setSpacing(10);
        portBox.setSpacing(10);

        //Create Spacer between Button and fields
        HBox spacer = new HBox();
        spacer.setMinHeight(30);

        //Add fields and Button to VBox
        root.getChildren().addAll(header, languagesBox, ipBox, portBox, spacer, buttonBox);
        root.setSpacing(10);

        //Create Scene
        Scene scene = new Scene(root, 450, 450);
        String stylesheet = sl.getClass().getResource("stylesheet.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);

        return scene;
    }


    protected void updateTexts() {
        try {
            this.tr = ServiceLocator.getServiceLocator().getTranslator();
            header.setText(tr.getString("header.settings"));
            choiceLabel.setText(tr.getString("preferences.languagesLbl"));
            saveSettingsBtn.setText(tr.getString("preferences.saveBtn"));
            ipAddressLbl.setText(tr.getString("preferences.ipAddressLbl"));
            portLbl.setText(tr.getString("preferences.portLbl"));
            logger.fine("Texts updated.");
        } catch (Exception e) {
            logger.warning("Problem with updating Texts. "+e.getStackTrace().toString());
        }
    }



    public void changeButton(){
        try {
            String url = System.getProperty("user.dir");
            url += "/src/assets/img/done_circle.png";
            File image = new File(url);
            url = image.toURI().toURL().toString();
            Image doneCircle = new Image(url);
            ImageView headerImage = new ImageView(doneCircle);
            headerImage.setFitHeight(20);
            headerImage.setFitWidth(20);
            savedButton = new Button(tr.getString("buttons.saved"), headerImage);
            savedButton.getStyleClass().add("success");
            buttonBox.getChildren().remove(saveSettingsBtn);
            buttonBox.getChildren().add(savedButton);
            logger.fine("Button changed.");
        } catch (Exception e){
            logger.warning("Button not changed. \n" +
                    "Stack Trace: "+e.getStackTrace().toString());

        }
    }
}
