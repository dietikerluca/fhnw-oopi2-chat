package src.preferencesClasses;

import src.commonViews.ChoicePopUp;
import src.Main.ErrorPopUp;
import src.ServiceLocator;
import src.abstractClasses.Controller;
import src.commonClasses.Translator;
import src.mainClasses.Main_View;

import java.util.logging.Logger;

public class Preferences_Controller extends Controller {

    //TODO src.ServiceLocator schon hier initalisieren?
    Main_View main_view;
    ServiceLocator sl = ServiceLocator.getServiceLocator();
    Translator tr = sl.getTranslator();
    Logger logger = sl.getLogger();

    public Preferences_Controller(Preferences_View view, Preferences_Model model, Main_View main_view){
        super(model, view);
        this.main_view = main_view;

//        view.languages.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            logger.info("Detected Language: "+newValue);
//            if (newValue.equals("Deutsch")) {
//                ServiceLocator.getServiceLocator().setTranslator(new Translator("de"));
//                main_view.mainMenu.updateTexts();
//                view.updateTexts();
//            }
//                if (newValue.equals("English")) {
//                    ServiceLocator.getServiceLocator().setTranslator(new Translator("en"));
//                    main_view.mainMenu.updateTexts();
//                    view.updateTexts();
//                }
//                view.updateTexts();
//            }
//
//        );

//        TODO vervollständigen
        view.saveSettingsBtn.setOnAction(click -> {
            logger.fine("Button click: Save");
            view.buttonPressed = true;
            if (view.ipAddressField.getText().equals("")){
                logger.fine("IP Adress field empty");
            } else if (model.validateIpAddress(view.ipAddressField.getText()) == false) {
                logger.fine("Invalid IP");
                ErrorPopUp errorPopUp = new ErrorPopUp(tr.getString("ErrorMessages.invalidIP"),
                        tr.getString("buttons.close"));
                view.ipAddressField.getStyleClass().add("invalid");

            } else {
                ServiceLocator.getServiceLocator().setIpAddressPreset(view.ipAddressField.getText());
                view.changeButton();
            }
            if (view.portField.getText().equals("")){
                logger.fine("Port field empty");
            } else {
                ServiceLocator.getServiceLocator().setPort(Integer.parseInt(view.portField.getText()));
                logger.fine("Save request transmitted.");
            }
        });

        //Listeners for incorrect value entries
        view.portField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 2){
                view.portField.getStyleClass().add("invalid");
                logger.info("Port number longer than 2");
            } else {
                view.portField.getStyleClass().remove("invalid");
                logger.fine("Port number shorter than 2");
            }
        });

        view.ipAddressField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (view.buttonPressed){
                if (model.validateIpAddress(newValue)) {
                    view.ipAddressField.getStyleClass().remove("invalid");
                    logger.fine("IP Adress seems to be valid now.");
                }
            }
        });

        view.ipAddressField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() == 3 && !newValue.endsWith(".") &&
                    !(oldValue.endsWith(".") && newValue.equals(oldValue.substring(0, 3)))) {
                view.ipAddressField.setText(view.ipAddressField.getText().concat("."));
                logger.finest("IP length: "+newValue.length());
            } else if (newValue.length() == 7 && !newValue.endsWith(".") &&
                    !(oldValue.endsWith(".") && newValue.equals(oldValue.substring(0, 7)))){
                view.ipAddressField.setText(view.ipAddressField.getText().concat("."));
                logger.finest("IP length: "+newValue.length());
            } else if (newValue.length() == 11 && !newValue.endsWith(".") &&
                    !(oldValue.endsWith(".") && newValue.equals(oldValue.substring(0, 11)))){
                view.ipAddressField.setText(view.ipAddressField.getText().concat("."));
                logger.finest("IP length: "+newValue.length());
            } else if (newValue.length() == 15 && !newValue.endsWith(".") &&
                    !(oldValue.endsWith(".") && newValue.equals(oldValue.substring(0, 15)))){
                view.ipAddressField.setText(view.ipAddressField.getText().concat("."));
                logger.finest("IP length: "+newValue.length());
            }
        });

        //Listener for re-editing after settings have been saved
        view.ipAddressField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (view.buttonPressed && view.buttonBox.getChildren().contains(view.savedButton)) {
                view.buttonBox.getChildren().remove(view.savedButton);
                view.buttonBox.getChildren().add(view.saveSettingsBtn);
                logger.finest("New inputs made.");
            }
        });

        //Listener for re-editing after settings have been saved
        view.portField.textProperty().addListener((observable, oldValue, newValue) -> {
            logger.finest("New inputs made.");
            if (view.buttonPressed && view.buttonBox.getChildren().contains(view.savedButton)) {
                view.buttonBox.getChildren().remove(view.savedButton);
                view.buttonBox.getChildren().add(view.saveSettingsBtn);
            }
        });

        //Register for closing events
//        view.getStage().setOnCloseRequest(event -> {
//            if (view.ipAddressField.getText().equals("") == false || view.portField.getText().equals("") == false){
//                logger.fine("User tries to close window, but there are unsaved changes.");
//                ChoicePopUp choicePopup = new ChoicePopUp(tr.getString("ErrorMessages.closeRequest"),
//                        tr.getString("buttons.back"), tr.getString("buttons.closeAnyway"),
//                        tr.getString("windows.closeRequest"));
//                event.consume();
//                choicePopup.secondaryBtn.setOnAction(click -> {
//                    view.stop();
//                    choicePopup.popUpStage.hide();
//                    logger.fine("Button: Close");
//                });
//                choicePopup.primaryBtn.setOnAction(click -> {
//                    choicePopup.popUpStage.hide();
//                    logger.fine("Button: Back");
//                });
//            }
//        });

    }



}
