package src.mainClasses;

import src.ServiceLocator;
import src.commonClasses.Translator;

import java.util.ArrayList;

public class Main_Model {
    ServiceLocator sl;
    Translator tr;

    public Main_Model(){
        this.sl = ServiceLocator.getServiceLocator();
        this.tr = sl.getTranslator();
    }
}
