package Main;

import java.util.logging.Logger;

public class Preferences_Controller {

    Preferences_View prefView;
    Messenger model;
    View view;
    ServiceLocator sl = ServiceLocator.getServiceLocator();
    Translator tr = sl.getTranslator();
    Logger logger = sl.getLogger();

    public Preferences_Controller(Preferences_View prefView, Messenger model, View view){
        this.prefView = prefView;
        this.model = model;
        this.view = view;

        prefView.languages.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.info("Detected Language: "+newValue);
            if (newValue.equals("Deutsch")) {
                ServiceLocator.getServiceLocator().setTranslator(new Translator("de"));
                view.mainMenu.updateTexts();
                prefView.updateTexts();
            }
                if (newValue.equals("English")) {
                    ServiceLocator.getServiceLocator().setTranslator(new Translator("en"));
                    view.mainMenu.updateTexts();
                    prefView.updateTexts();
                }
                prefView.updateTexts();
            }

        );

//        TODO vervollständigen
        prefView.saveSettingsBtn.setOnAction(click -> {
            logger.fine("Button click: Save");
            prefView.buttonPressed = true;
            if (prefView.ipAddressField.getText().equals("")){
                logger.fine("IP Adress field empty");
            } else if (prefView.validateIpAddress(prefView.ipAddressField.getText()) == false) {
                logger.fine("Invalid IP");
                ErrorPopUp errorPopUp = new ErrorPopUp(tr.getString("ErrorMessages.invalidIP"),
                        tr.getString("buttons.close"));
                prefView.ipAddressField.getStyleClass().add("invalid");

            } else {
                ServiceLocator.getServiceLocator().setIpAddressPreset(prefView.ipAddressField.getText());
                prefView.changeButton();
            }
            if (prefView.portField.getText().equals("")){
                logger.fine("Port field empty");
            } else {
                ServiceLocator.getServiceLocator().setPort(Integer.parseInt(prefView.portField.getText()));
            }
        });

    }



}
