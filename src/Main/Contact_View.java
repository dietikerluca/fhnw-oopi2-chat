package Main;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.logging.Logger;


public class Contact_View {

    Translator tr = ServiceLocator.getServiceLocator().getTranslator();

    Stage contactStage;
    Messenger model;
    TextField firstName, lastName, username;
    Button saveBtn;
    View view;
    Contact received;
    Logger logger = ServiceLocator.getServiceLocator().getLogger();

            public Contact_View(Messenger messenger, View view){
                received = null;
                this.model = messenger;
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

                //Create new Controller
                Contact_Controller controller = new Contact_Controller(model, this, view);
                logger.config("Contact Controller created.");


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

    public Contact_View(Messenger messenger, View view, Contact contact){
                received = contact;
        this.model = messenger;
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

        //Add Contact details already delivered
        firstName.setText(contact.getPrename());
        lastName.setText(contact.getLastname());
        username.setText(contact.getUsername());

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

        //Create new Controller
        Contact_Controller controller = new Contact_Controller(model, this, view);


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


    }

            public void stop(){
                this.contactStage.hide();
            }

            public Contact getExisting(){
                return this.received;
            }


}
