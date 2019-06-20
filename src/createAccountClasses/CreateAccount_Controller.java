package src.createAccountClasses;

import src.ChatApp;
import src.ServiceLocator;
import src.abstractClasses.Controller;
import src.commonClasses.Translator;
import src.commonViews.ChoicePopUp;
import src.commonViews.ErrorPopUp;

import java.util.logging.Logger;

public class CreateAccount_Controller extends Controller {

    private ChatApp main;
    private ServiceLocator sl = ServiceLocator.getServiceLocator();
    private Translator tr = sl.getTranslator();
    private Logger logger = sl.getLogger();

    public CreateAccount_Controller(ChatApp main, CreateAccount_Model model, CreateAccount_View view) {
        super(model, view);
        this.main = main;

        view.confirmButton.setOnAction(event -> {
            logger.fine("Button: Confirm");

            model.setUsername(view.usernameField.getText());
            model.setPassword(view.passwordField.getText());

            if (model.createAccount()) {
                ChoicePopUp success = new ChoicePopUp(
                        tr.getString("CreateAccountView.success"),
                        tr.getString("buttons.yeah"),
                        tr.getString("windows.AccountSuccess"));

                success.secondaryBtn.setOnAction(event1 -> {
                    main.startApp(model.getUsername());
                });

            } else {
                ErrorPopUp error = new ErrorPopUp(
                        tr.getString("ErrorMessages.accountCreation"),
                        tr.getString("buttons.tryAgain"));
            }
        });
    }
}
