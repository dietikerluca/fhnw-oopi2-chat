package src.Main;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import src.ServiceLocator;
import src.typeClasses.Person;

import java.io.File;
import java.util.logging.Logger;

public class ContactCard extends HBox {
    VBox contactInfoContainer;
    Label name, secondText;
    Ellipse status;
    private Person person;
    ServiceLocator sl = ServiceLocator.getServiceLocator();
    Logger logger = sl.getLogger();

    public ContactCard(Person person){

        this.name = new Label(person.getFirstname()+" "+person.getLastname()); //TODO
        this.secondText = new Label("Last seen: 22.06.2019"); //TODO

        if (person.isBlocked()) {
            /*Load BlockedImage
             * ----------------------------------------*/
            try {
                String url = System.getProperty("user.dir");
                url += "/src/assets/img/block_black.png";
                File image = new File(url);
                url = image.toURI().toURL().toString();
                Image blockedBlack = new Image(url);
                ImageView blockedIcon = new ImageView(blockedBlack);
                blockedIcon.setFitHeight(30);
                blockedIcon.setFitWidth(30);
                blockedIcon.getStyleClass().add("headerIcon");
                this.getChildren().add(blockedIcon);
            } catch (Exception e) {
                logger.warning("src.assets could not be loaded.");
            }

            this.name.getStyleClass().add("TextContactCardBlocked");
            this.secondText.getStyleClass().add("TextContactCardSecondBlocked");

        } else {
            this.status = new Ellipse(5,5); //TODO
            this.status.setFill(Color.GREEN); //TODO
            this.name.getStyleClass().add("TextContactCard");
            this.secondText.getStyleClass().add("TextContactCardSecond");
            this.getChildren().add(status);
        }
        this.person = person;
        this.getStyleClass().add("ContactCard");
        this.contactInfoContainer = new VBox();
        this.contactInfoContainer.getStyleClass().add("ContactCardTextContainer");
        this.contactInfoContainer.getChildren().addAll(this.name, this.secondText);
        this.getChildren().add(this.contactInfoContainer);

    }

    public Person getPerson(){
        return this.person;
    }

}