package src.serverChangeClasses;

import src.ChatApp;
import src.ServiceLocator;
import src.abstractClasses.Controller;
import src.commonClasses.Translator;
import src.commonViews.ChoicePopUp;
import src.commonViews.ErrorPopUp;
import src.splashScreen.Splash_Controller;
import src.splashScreen.Splash_Model;
import src.splashScreen.Splash_View;
import src.typeClasses.Chat;

import java.io.IOException;
import java.util.logging.Logger;

public class ServerChange_Controller extends Controller {

    public ServerChange_Controller(ServerChange_Model model, ServerChange_View view, ChatApp main) {
        super(model, view);

        ServiceLocator sl = ServiceLocator.getServiceLocator();
        Logger logger = sl.getLogger();
        Translator tr = sl.getTranslator();

        view.confirm.setOnAction(click -> {
            boolean changesMade = false;
            view.buttonPressed = true;
            logger.fine("Button click: Save");
            if (view.ipAddress.getText().equals("")){
                logger.fine("IP Adress field empty");
            } else if (model.getPrefModel().validateIpAddress(view.ipAddress.getText()) == false) {
                logger.fine("Invalid IP");
                ErrorPopUp errorPopUp = new ErrorPopUp(tr.getString("ErrorMessages.invalidIP"),
                        tr.getString("buttons.close"));
                view.ipAddress.getStyleClass().add("invalid");
            } else {
                ServiceLocator.getServiceLocator().setIpAddressPreset(view.ipAddress.getText());
                changesMade = true;
                view.changeButton();
            }
            if (view.port.getText().equals("")){
                logger.fine("Port field empty");
            } else {
                ServiceLocator.getServiceLocator().setPort(Integer.parseInt(view.port.getText()));
                changesMade = true;
                logger.fine("Save request transmitted.");
                view.changeButton();
            }

            if (changesMade) {
                sl.getChatClient().disconnect();
                boolean connected = sl.getChatClient(true).connect();
                if (!connected) {
                    ErrorPopUp errorPopUp = new ErrorPopUp(tr.getString("ErrorMessages.serverError") + " Unable to connect",
                            tr.getString("buttons.close"));
                } else {
                    view.stop();
                }
            }
        });

        //Listeners for incorrect value entries
        view.port.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 2){
                view.port.getStyleClass().add("invalid");
                logger.info("Port number longer than 2");
            } else {
                view.port.getStyleClass().remove("invalid");
                logger.fine("Port number shorter than 2");
            }
        });

        view.ipAddress.textProperty().addListener((observable, oldValue, newValue) -> {
            if (view.buttonPressed){
                if (model.getPrefModel().validateIpAddress(newValue)) {
                    view.ipAddress.getStyleClass().remove("invalid");
                    logger.fine("IP Adress seems to be valid now.");
                }
            }
        });

        view.ipAddress.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() == 3 && !newValue.endsWith(".") &&
                    !(oldValue.endsWith(".") && newValue.equals(oldValue.substring(0, 3)))) {
                view.ipAddress.setText(view.ipAddress.getText().concat("."));
                logger.finest("IP length: "+newValue.length());
            } else if (newValue.length() == 7 && !newValue.endsWith(".") &&
                    !(oldValue.endsWith(".") && newValue.equals(oldValue.substring(0, 7)))){
                view.ipAddress.setText(view.ipAddress.getText().concat("."));
                logger.finest("IP length: "+newValue.length());
            } else if (newValue.length() == 11 && !newValue.endsWith(".") &&
                    !(oldValue.endsWith(".") && newValue.equals(oldValue.substring(0, 11)))){
                view.ipAddress.setText(view.ipAddress.getText().concat("."));
                logger.finest("IP length: "+newValue.length());
            }
        });

        //Listener for re-editing after settings have been saved
        view.ipAddress.textProperty().addListener((observable, oldValue, newValue) -> {
            if (view.buttonPressed && view.root.getChildren().contains(view.savedButton)) {
                view.root.getChildren().remove(view.savedButton);
                view.root.getChildren().add(view.confirm);
                logger.finest("New inputs made.");
            }
        });

        //Listener for re-editing after settings have been saved
        view.port.textProperty().addListener((observable, oldValue, newValue) -> {
            logger.finest("New inputs made.");
            if (view.buttonPressed && view.root.getChildren().contains(view.savedButton)) {
                view.root.getChildren().remove(view.savedButton);
                view.root.getChildren().add(view.confirm);
            }
        });

        //Register for closing events
        view.getStage().setOnCloseRequest(event -> {
            if ((view.ipAddress.getText().equals("") == false || view.port.getText().equals("") == false) &&
            view.buttonPressed == false){
                logger.fine("User tries to close window, but there are unsaved changes.");
                ChoicePopUp choicePopup = new ChoicePopUp(tr.getString("ErrorMessages.closeRequest"),
                        tr.getString("buttons.back"), tr.getString("buttons.closeAnyway"),
                        tr.getString("windows.closeRequest"));
                event.consume();
                choicePopup.secondaryBtn.setOnAction(click -> {
                    view.stop();
                    choicePopup.popUpStage.hide();
                    logger.fine("Button: Close");
                });
                choicePopup.primaryBtn.setOnAction(click -> {
                    choicePopup.popUpStage.hide();
                    logger.fine("Button: Back");
                });
            }
        });

    }
}
