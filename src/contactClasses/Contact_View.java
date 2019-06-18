package src.contactClasses;

import src.ServiceLocator;
import src.commonClasses.Translator;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import src.mainClasses.Main_Model;
import src.mainClasses.Main_View;

import java.io.File;
import java.util.logging.Logger;


public class Contact_View {

    Translator tr = ServiceLocator.getServiceLocator().getTranslator();

    Stage contactStage;
    Main_Model model;
    TextField firstName, lastName, username;
    Button saveBtn;
    ToggleButton blocked;
    ImageView blockedIconWhite;
    Main_View view;
    Contact received;
    HBox buttons;
    Logger logger = ServiceLocator.getServiceLocator().getLogger();

            public Contact_View(Main_Model mainModel, Main_View mainView){
                received = null;
                this.model = mainModel;
                VBox verticalBox = new VBox();
                verticalBox.getStyleClass().add("windowPopUp");
                HBox labelBox = new HBox();
                labelBox.getStyleClass().add("nameBoxContact");
                Label header = new Label(tr.getString("labels.newContact"));
                header.getStyleClass().add("header");
                labelBox.getChildren().add(header);
                HBox nameBox = new HBox();
                HBox userNameBox = new HBox();
                nameBox.getStyleClass().add("nameBoxContact");
                userNameBox.getStyleClass().add("nameBoxContact");
                firstName = new TextField();
                firstName.getStyleClass().add("contact");
                lastName = new TextField();
                lastName.getStyleClass().add("contact");
                username = new TextField();
                username.getStyleClass().add("contact");
                firstName.setPromptText(tr.getString("contactWindow.firstName"));
                lastName.setPromptText(tr.getString("contactWindow.lastName"));
                username.setPromptText(tr.getString("contactWindow.userName"));
                nameBox.getChildren().addAll(firstName, lastName);
                userNameBox.getChildren().addAll(username);
                saveBtn = new Button(tr.getString("contactWindow.saveBtn"));
                saveBtn.setDisable(true);
                saveBtn.getStyleClass().add("confirm");
                HBox spacer = new HBox();
                spacer.setMinHeight(30);
                verticalBox.getChildren().addAll(labelBox, nameBox, userNameBox, spacer, saveBtn);

                //Block Button
                //TODO Block Button here too?

                //Show stage
                Scene scene = new Scene(verticalBox, 400, 350);

                //Link Stylesheet
                String stylesheet = getClass().getResource("stylesheet.css").toExternalForm();
                scene.getStylesheets().add(stylesheet);
                contactStage = new Stage();
                contactStage.setTitle(tr.getString("windows.newContact"));
                contactStage.setScene(scene);
                contactStage.setMinHeight(350);
                contactStage.setMinWidth(400);

                contactStage.show();

                //Create new Main_Controller
                Contact_Controller controller = new Contact_Controller(model, this, mainView);
                logger.config("Contact Main_Controller created.");


                /*Checks if Textfields are empty
                * ---------------------------------*/

                firstName.textProperty().addListener((observable, oldValue, newValue) -> {
                    logger.finest("Change: FirstName");
                    if (!newValue.equals("") && !newValue.isEmpty() && (!lastName.getText().isEmpty()) &&
                            (!username.getText().isEmpty())){
                        saveBtn.setDisable(false);
                        logger.fine("Save Button enabled.");
                    } else {
                        saveBtn.setDisable(true);
                        logger.fine("Save Button disabled.");
                    }

                });
                lastName.textProperty().addListener((observable, oldValue, newValue) -> {
                    logger.finest("Change: LastName");
                    if (!newValue.equals("") && !newValue.isEmpty() && (!firstName.getText().isEmpty()) &&
                            (!username.getText().isEmpty())){
                        saveBtn.setDisable(false);
                        logger.fine("Save Button enabled.");
                    } else {
                        saveBtn.setDisable(true);
                        logger.fine("Save Button disabled.");
                    }

                });
                username.textProperty().addListener((observable, oldValue, newValue) -> {
                    logger.finest("Change: UserName");
                    if (!newValue.equals("") && !newValue.isEmpty() && (!lastName.getText().isEmpty()) &&
                            (!firstName.getText().isEmpty())){
                        saveBtn.setDisable(false);
                        logger.fine("Save Button enabled.");
                    } else {
                        saveBtn.setDisable(true);
                        logger.fine("Save Button disabled.");
                    }

                });


            }

    public Contact_View(Main_Model mainModel, Main_View mainView, Contact contact){
                received = contact;
        this.model = mainModel;
        VBox verticalBox = new VBox();
        verticalBox.getStyleClass().add("windowPopUp");
        HBox labelBox = new HBox();
        labelBox.getStyleClass().add("nameBoxContact");
        Label header = new Label(tr.getString("labels.newContact"));
        header.getStyleClass().add("header");
        labelBox.getChildren().add(header);
        HBox nameBox = new HBox();
        HBox userNameBox = new HBox();
        nameBox.getStyleClass().add("nameBoxContact");
        userNameBox.getStyleClass().add("nameBoxContact");
        firstName = new TextField();
        firstName.getStyleClass().add("contact");
        lastName = new TextField();
        lastName.getStyleClass().add("contact");
        username = new TextField();
        username.getStyleClass().add("contact");
        firstName.setPromptText(tr.getString("contactWindow.firstName"));
        lastName.setPromptText(tr.getString("contactWindow.lastName"));
        username.setPromptText(tr.getString("contactWindow.userName"));
        nameBox.getChildren().addAll(firstName, lastName);
        userNameBox.getChildren().addAll(username);
        saveBtn = new Button(tr.getString("contactWindow.saveBtn"));
        saveBtn.setDisable(true);
        saveBtn.getStyleClass().add("confirm");
        HBox spacer = new HBox();
        buttons = new HBox();
        buttons.setSpacing(20);
        spacer.setMinHeight(30);
        verticalBox.getChildren().addAll(labelBox, nameBox, userNameBox, spacer, buttons);

        //Add Contact details already delivered
        firstName.setText(contact.getPrename());
        lastName.setText(contact.getLastname());
        username.setText(contact.getUsername());

        //Block Button
        /*Load src.assets
         * ----------------------------------------*/
        try {
            String url = System.getProperty("user.dir");
            url += "/src/assets/img/block_white.png";
            File image = new File(url);
            url = image.toURI().toURL().toString();
            Image errorRobo = new Image(url);
            blockedIconWhite = new ImageView(errorRobo);
            blockedIconWhite.setFitHeight(20);
            blockedIconWhite.setFitWidth(20);
        } catch (Exception e){
            logger.warning("src.assets could not be loaded.");
        }
        blocked = new ToggleButton( "--", blockedIconWhite);
        blocked.getStyleClass().add("block");
        if (contact.isBlocked()) {
            blocked.setSelected(true);
            blocked.setText(tr.getString("buttons.unblocked"));
        } else {
            blocked.setSelected(false);
            blocked.setText(tr.getString("buttons.blocked"));
        }
        buttons.getChildren().addAll(blocked, saveBtn);

        //Show stage
        Scene scene = new Scene(verticalBox, 400, 350);

        //Link Stylesheet
        String stylesheet = getClass().getResource("stylesheet.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);
        contactStage = new Stage();
        contactStage.setTitle(tr.getString("windows.newContact"));
        contactStage.setScene(scene);
        contactStage.setMinHeight(350);
        contactStage.setMinWidth(400);

        contactStage.show();

        //Create new Main_Controller
        Contact_Controller controller = new Contact_Controller(model, this, mainView);


        /*Checks if Textfields are empty
         * ---------------------------------*/

        firstName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals("") && !newValue.isEmpty() && (!lastName.getText().isEmpty()) &&
                    (!username.getText().isEmpty())){
                saveBtn.setDisable(false);
            } else {
                saveBtn.setDisable(true);
            }

        });
        lastName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals("") && !newValue.isEmpty() && (!firstName.getText().isEmpty()) &&
                    (!username.getText().isEmpty())){
                saveBtn.setDisable(false);
            } else {
                saveBtn.setDisable(true);
            }

        });
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals("") && !newValue.isEmpty() && (!lastName.getText().isEmpty()) &&
                    (!firstName.getText().isEmpty())){
                saveBtn.setDisable(false);
            } else {
                saveBtn.setDisable(true);
            }

        });

        blocked.setOnAction(event -> {
            if ((!lastName.getText().isEmpty())
                    && (!firstName.getText().isEmpty())
                    && (!username.getText().isEmpty())){
                    saveBtn.setDisable(false);
            } else {
                saveBtn.setDisable(true);
            }

        });

        /*Checks for Button hovers (Toggle Block Button
        * ---------------------------------------------*/
        blocked.setOnMouseEntered(event -> {
            if (blocked.isSelected()){
                blocked.setText(tr.getString("buttons.unblock"));
            } else {
                blocked.setText(tr.getString("buttons.block"));
            }
        });

        blocked.setOnMouseExited(event -> {
            if (blocked.isSelected()){
                blocked.setText(tr.getString("buttons.blocked"));
            } else {
                blocked.setText(tr.getString("buttons.unblocked"));
            }
        });


    }

            public void stop(){
                this.contactStage.hide();
            }

            public Contact getExisting(){
                return this.received;
            }



}
