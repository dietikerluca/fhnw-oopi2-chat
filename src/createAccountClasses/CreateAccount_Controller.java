package src.createAccountClasses;

import src.ChatApp;
import src.ServiceLocator;
import src.abstractClasses.Controller;
import src.commonClasses.Translator;
import src.commonViews.ChoicePopUp;
import src.commonViews.ErrorPopUp;

import java.util.logging.Logger;

public class CreateAccount_Controller extends Controller {
    private ServiceLocator sl;
    private Translator tr;
    private Logger logger;

    public CreateAccount_Controller(ChatApp main, CreateAccount_Model model, CreateAccount_View view) {
        super(model, view);
        sl = ServiceLocator.getServiceLocator();
        tr = sl.getTranslator();
        logger = sl.getLogger();

        view.confirmButton.setOnAction(event -> {
            logger.fine("Button: Confirm");

            model.setUsername(view.usernameField.getText());
            model.setPassword(view.passwordField.getText());

            if (model.createAccount()) {
                ChoicePopUp success = new ChoicePopUp(
                        tr.getString("CreateAccountView.success"),
                        tr.getString("buttons.yeah"),
                        tr.getString("windows.AccountSuccess"));

                success.secondaryBtn.setOnAction(successEvent -> {
                    success.stop();
                    main.startApp(model.getUsername());
                });

            } else {
                new ErrorPopUp(tr.getString("ErrorMessages.accountCreation"), tr.getString("buttons.tryAgain"));
            }
        });
    }
}
