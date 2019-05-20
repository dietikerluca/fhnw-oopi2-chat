package Main;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class ContactCard extends HBox {
    VBox contactInfoContainer;
    Label name, secondText;
    Ellipse status;
    private Contact contact;

    public ContactCard(Contact contact){
        this.contact = contact;
        this.setId("ContactListCardContainer");
        this.contactInfoContainer = new VBox();
        this.contactInfoContainer.setId("ContactCardTextContainer");
        this.status = new Ellipse(5,5); //TODO
        this.status.setFill(Color.GREEN); //TODO
        this.name = new Label(contact.getPrename()+" "+contact.getSurname()); //TODO
        this.name.setId("TextContactCard");
        this.secondText = new Label("Last seen: 22.06.2019"); //TODO
        this.secondText.setId("TextContactCardSecond");
        this.contactInfoContainer.getChildren().addAll(this.name, this.secondText);
        this.getChildren().addAll(this.status, this.contactInfoContainer);
    }

    public Contact getContact(){
        return this.contact;
    }

}