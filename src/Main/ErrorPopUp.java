package Main;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ErrorPopUp {

    Stage errorStage;

    public ErrorPopUp(String errorMessageString){
        errorStage = new Stage();
        Stage errorStage = new Stage();
        VBox vbox = new VBox();
        Label errorMessage = new Label(errorMessageString);
        Button closeBtn = new Button("Close");
        closeBtn.setId("closeBtn");
        vbox.getChildren().addAll(errorMessage, closeBtn);
        vbox.setSpacing(20);
        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox,200,150);
        String stylesheet = getClass().getResource("stylesheet.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);
        errorStage.setScene(scene);
        errorStage.setTitle("Error");
        errorStage.centerOnScreen();
        errorStage.setAlwaysOnTop(true);
        errorStage.setMinWidth(200);
        errorStage.setMinHeight(150);
        errorStage.setMaxWidth(200);
        errorStage.setMaxHeight(150);
        errorStage.show();
        closeBtn.setOnAction(event -> {
            errorStage.close();
        });
    }
}
