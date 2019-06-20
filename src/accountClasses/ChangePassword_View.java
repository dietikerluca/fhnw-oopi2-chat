package src.accountClasses;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import src.ServiceLocator;
import src.abstractClasses.View;
import src.commonClasses.Translator;

import java.io.File;
import java.util.logging.Logger;

public class ChangePassword_View extends View {

    public PasswordField newPassword, confirmPassword;
    public Button confirm, confirmedButton;
    public VBox root;
    private Logger logger;
    private Translator tr;

    public ChangePassword_View(Stage stage, ChangePassword_Model model) {
        super(stage, model);
    }

    @Override
    protected Scene create_GUI() {
        ServiceLocator sl = ServiceLocator.getServiceLocator();
        logger = sl.getLogger();
        tr = sl.getTranslator();

        root = new VBox();

        // Create Fields and Buttons
        Label header = new Label(tr.getString("header.changePassword"));
        Label passwordLabel = new Label(tr.getString("labels.newPassword"));
        Label passwordConfirmLabel = new Label(tr.getString("labels.confirmPassword"));
        newPassword = new PasswordField();
        confirmPassword = new PasswordField();

        confirm = new Button(tr.getString("buttons.save"));
        HBox spacer = new HBox();
        spacer.setMinHeight(20);

        // Styling
        header.getStyleClass().add("header");
        passwordLabel.getStyleClass().add("defaultLabel");
        passwordConfirmLabel.getStyleClass().add("defaultLabel");
        confirm.getStyleClass().add("confirm");

        root.getChildren().addAll(header,
                passwordLabel, newPassword,
                passwordConfirmLabel, confirmPassword,
                spacer, confirm);
        root.setSpacing(20);
        root.getStyleClass().add("windowPopUp");

        // Create Scene and add Stylesheet
        Scene scene = new Scene(root, 400, 450);
        String stylesheet = sl.getClass().getResource("stylesheet.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);

        return scene;
    }

    public void changeButton(){
        try {
            String url = System.getProperty("user.dir");
            url += "/src/assets/img/done_circle.png";
            File image = new File(url);
            url = image.toURI().toURL().toString();
            Image doneCircle = new Image(url);
            ImageView headerImage = new ImageView(doneCircle);
            headerImage.setFitHeight(20);
            headerImage.setFitWidth(20);
            confirmedButton = new Button(tr.getString("buttons.saved"), headerImage);
            confirmedButton.getStyleClass().add("success");
            root.getChildren().remove(confirm);
            root.getChildren().add(confirmedButton);
            logger.fine("Button changed.");
        } catch (Exception e){
            logger.warning("Button not changed. \n" +
                    "Stack Trace: "+e.getStackTrace().toString());

        }
    }
}
