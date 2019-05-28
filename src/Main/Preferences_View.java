package Main;

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

import java.io.File;
import java.util.regex.Pattern;

public class Preferences_View {

    public Stage preferences;
    public Messenger model;
    public ChoiceBox<String> languages;
    private ServiceLocator sl = ServiceLocator.getServiceLocator();
    private Translator tr = sl.getTranslator();
    public Button saveSettingsBtn, savedButton;
    public TextField ipAddressField, portField;
    public Label choiceLabel, ipAddressLbl, portLbl, header;
    public boolean buttonPressed = false;
    private HBox buttonBox;

    public Preferences_View(Messenger modelAccess){
        preferences = new Stage();
        this.model = modelAccess;
        VBox vbox = new VBox();
        vbox.getStyleClass().add("windowPopUp");

        //Create Fields
        header = new Label(tr.getString("header.settings"));
        header.getStyleClass().add("header");
        languages = new ChoiceBox<String>();
        choiceLabel = new Label(tr.getString("preferences.languagesLbl"));
        languages.getItems().addAll("English","Deutsch");
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
        portField.setMaxWidth(40);

        //Set Promt Texts
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
        vbox.getChildren().addAll(header, languagesBox, ipBox, portBox, spacer, buttonBox);
        vbox.setSpacing(10);

        //Create Scene
        Scene scene = new Scene(vbox, 450, 450);
        String stylesheet = getClass().getResource("stylesheet.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);
        preferences.setMinHeight(450);
        preferences.setMinWidth(450);
        preferences.setTitle(tr.getString("windows.settings"));
        preferences.setScene(scene);

        //Listeners for incorrect value entries
        portField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 2){
                portField.getStyleClass().add("invalid");
            } else {
                portField.getStyleClass().remove("invalid");
            }
        });

        ipAddressField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (buttonPressed){
                if (validateIpAddress(newValue)) {
                    ipAddressField.getStyleClass().remove("invalid");
                }
            }
        });

        ipAddressField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() == 3){
                ipAddressField.setText(ipAddressField.getText().concat("."));
            } else if (newValue.length() == 7){
                ipAddressField.setText(ipAddressField.getText().concat("."));
            } else if (newValue.length() == 11){
                ipAddressField.setText(ipAddressField.getText().concat("."));
            } else if (newValue.length() == 11){
                ipAddressField.setText(ipAddressField.getText().concat("."));
            }
        });

        //Listener for re-editing after settings have been saved
        ipAddressField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (buttonPressed && buttonBox.getChildren().contains(savedButton)) {
                buttonBox.getChildren().remove(savedButton);
                buttonBox.getChildren().add(saveSettingsBtn);
            }
        });

        //Listener for re-editing after settings have been saved
        portField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (buttonPressed && buttonBox.getChildren().contains(savedButton)) {
                buttonBox.getChildren().remove(savedButton);
                buttonBox.getChildren().add(saveSettingsBtn);
            }
        });

        //Register for closing events
        preferences.setOnCloseRequest(event -> {
            if (ipAddressField.getText().equals("") == false){
                ChoicePopUp choicePopup = new ChoicePopUp(tr.getString("ErrorMessages.closeRequest"),
                        tr.getString("buttons.back"), tr.getString("buttons.closeAnyway"),
                        tr.getString("windows.closeRequest"));
                event.consume();
                choicePopup.secondaryBtn.setOnAction(click -> {
                    this.stop();
                    choicePopup.popUpStage.hide();
                });
            }
        });

    }

    public void start(){
        preferences.show();
    }

    public void stop() {
        preferences.hide();
    }

    protected void updateTexts() {
//        The menu entries
        this.tr = ServiceLocator.getServiceLocator().getTranslator();
        header.setText(tr.getString("header.settings"));
        choiceLabel.setText(tr.getString("preferences.languagesLbl"));
        saveSettingsBtn.setText(tr.getString("preferences.saveBtn"));
        ipAddressLbl.setText(tr.getString("preferences.ipAddressLbl"));
        portLbl.setText(tr.getString("preferences.portLbl"));
    }

    /* Partly Copied from Bradley Richards
     * -----------------------------------------------------------------------------------*/
    public static boolean validateIpAddress(String ipAddress) {
        boolean formatOK = true;
        boolean numbersOnly = true;
        String ipPieces[] = ipAddress.split("\\.");
        for (String piece : ipPieces) {
            if (Pattern.matches("[a-zA-Z]+", piece)) {
                numbersOnly = false;
            }
        }
         if (numbersOnly) {
            if (ipPieces.length != 4) {
                formatOK = false;
            } else {
                // Each part must be an integer 0 to 255
                int byteValue = -1;
                for (String s : ipPieces) {
                    byteValue = Integer.parseInt(s); // may throw
                    // NumberFormatException
                    if (byteValue < 0 | byteValue > 255) formatOK = false;
                }
            }
        }
        return (formatOK && numbersOnly);
    }

    public static boolean validatePortNumber(String portText) {
        boolean formatOK = false;
        try {
            int portNumber = Integer.parseInt(portText);
            if (portNumber >= 0 & portNumber <= 65535) {
                formatOK = true;
            }
        } catch (NumberFormatException e) {
        }
        return formatOK;
    }

    public void changeButton(){

        try {
            String url = System.getProperty("user.dir");
            url += "/src/Image/done_circle.png";
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
        } catch (Exception e){

        }
    }
}
