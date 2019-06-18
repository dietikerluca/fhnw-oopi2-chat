package src.mainClasses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import src.ServiceLocator;
import src.commonClasses.Translator;
import src.typeClasses.Person;

import java.util.ArrayList;

public class Main_Model {
    ServiceLocator sl;
    Translator tr;
    ObservableList<Person> persons;

    public Main_Model() {
        this.sl = ServiceLocator.getServiceLocator();
        this.tr = sl.getTranslator();

        persons = FXCollections.observableArrayList();
    }
}
