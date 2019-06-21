package src.accountClasses;

import src.ServiceLocator;
import src.abstractClasses.Controller;
import src.commonClasses.Translator;
import src.commonViews.ErrorPopUp;

import java.util.logging.Logger;

public class ChangePassword_Controller extends Controller {

    public ChangePassword_Controller(ChangePassword_Model model, ChangePassword_View view) {
        super(model, view);

        ServiceLocator sl = ServiceLocator.getServiceLocator();
        Translator tr = sl.getTranslator();
        Logger logger = sl.getLogger();

        view.confirm.setOnAction(event -> {
            logger.fine("Button: Confirm password change.");
            if (view.newPassword.getText() == "" || view.confirmPassword.getText() == ""){
                ErrorPopUp errorPopUp = new ErrorPopUp(tr.getString("ErrorMessages.changePassword"),
                        tr.getString("buttons.close"));
                logger.warning("Password field is empty");
            } else if (view.newPassword.getText().equals(view.confirmPassword.getText()) == false){
                ErrorPopUp errorPopUp = new ErrorPopUp(tr.getString("ErrorMessages.passwordNotMatching"),
                        tr.getString("buttons.close"));
                logger.warning("New passwords do not match");
            } else {
                model.changePassword(view.newPassword.getText());
                view.changeButton();
                logger.fine("Password change request sent.");
                if (model.isPasswordChanged()){

                } else {
                    ErrorPopUp notChanged = new ErrorPopUp(tr.getString("ErrorMessages.pwNotchanged"),
                            tr.getString("buttons.tryAgain"));
                }
            }
        });
    }
}
