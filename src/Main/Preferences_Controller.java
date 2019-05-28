package Main;

public class Preferences_Controller {

    Preferences_View prefView;
    Messenger model;
    View view;
    Translator tr = ServiceLocator.getServiceLocator().getTranslator();

    public Preferences_Controller(Preferences_View prefView, Messenger model, View view){
        this.prefView = prefView;
        this.model = model;
        this.view = view;

        prefView.languages.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
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
            prefView.buttonPressed = true;
            if (prefView.ipAddressField.getText().equals("")){
            } else if (prefView.validateIpAddress(prefView.ipAddressField.getText()) == false) {
                ErrorPopUp errorPopUp = new ErrorPopUp(tr.getString("ErrorMessages.invalidIP"),
                        tr.getString("buttons.close"));
                prefView.ipAddressField.getStyleClass().add("invalid");
            } else {
                ServiceLocator.getServiceLocator().setIpAddressPreset(prefView.ipAddressField.getText());
                prefView.changeButton();
            }
            if (prefView.portField.getText().equals("")){
            } else {
                ServiceLocator.getServiceLocator().setPort(Integer.parseInt(prefView.portField.getText()));
            }
        });

    }



}
