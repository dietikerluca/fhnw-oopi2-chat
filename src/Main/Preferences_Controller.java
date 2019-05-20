package Main;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.Locale;

public class Preferences_Controller {

    Preferences prefView;
    Messenger model;
    View view;

    public Preferences_Controller(Preferences prefView, Messenger model, View view){
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
            }
        );

//        TODO vervollständigen
//        prefView.saveSettingsBtn.setOnAction(click -> {
//            if (prefView.ipAddressField.getText().equals("")){
//            } else {
//                ServiceLocator.getServiceLocator().setIpPreset(();
//            }
//        });

    }

}
