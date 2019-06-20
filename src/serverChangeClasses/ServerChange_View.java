package src.serverChangeClasses;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import src.ServiceLocator;
import src.abstractClasses.Model;
import src.abstractClasses.View;

import java.io.File;
import java.util.logging.Logger;


public class ServerChange_View extends View {

    public TextField ipAddress, port;
    public Button confirm, savedButton;
    private Label header, ipLabel, portLabel;
    protected boolean buttonPressed = false;
    protected VBox root;
    private Logger logger;

    /**
     * Set any options for the stage in the subclass constructor
     *
     * @param stage
     * @param model
     */
    public ServerChange_View(Stage stage, Model model) {
        super(stage, model);

        // Stage config
        stage.setMaxWidth(400);
        stage.setMaxHeight(450);
        stage.setTitle("Server Configuration");
        stage.setAlwaysOnTop(true);
    }

    @Override
    protected Scene create_GUI() {
        ServiceLocator sl = ServiceLocator.getServiceLocator();
        logger = sl.getLogger();

        root = new VBox();

        // Create Fields and Buttons
        header = new Label("Server configuration");
        ipLabel = new Label("IP Address:");
        portLabel = new Label("Port:");
        ipAddress = new TextField();
        ipAddress.setText(sl.getIpAddressPreset());
        port = new TextField();
        port.setText(Integer.toString(sl.getPort()));
        confirm = new Button("Save");
        HBox spacer = new HBox();
        spacer.setMinHeight(20);

        // Styling
        header.getStyleClass().add("header");
        ipLabel.getStyleClass().add("defaultLabel");
        portLabel.getStyleClass().add("defaultLabel");
        confirm.getStyleClass().add("confirm");

        port.setMaxWidth(40);

        root.getChildren().addAll(header, ipLabel, ipAddress, portLabel, port, spacer, confirm);
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
            savedButton = new Button("Saved", headerImage);
            savedButton.getStyleClass().add("success");
            root.getChildren().remove(confirm);
            root.getChildren().add(savedButton);
            logger.fine("Button changed.");
        } catch (Exception e){
            logger.warning("Button not changed. \n" +
                    "Stack Trace: "+e.getStackTrace().toString());

        }
    }
}
