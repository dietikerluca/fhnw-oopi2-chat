package src.serverChangeClasses;

import src.abstractClasses.Model;
import src.preferencesClasses.Preferences_Model;

public class ServerChange_Model extends Model {

    Preferences_Model prefModel;

    public ServerChange_Model(){
        prefModel = new Preferences_Model();
    }

    public Preferences_Model getPrefModel() {
        return prefModel;
    }
}
