package Main;

public class Messenger {
    ServiceLocator sl;
    Translator tr;

    public void Messenger(){
        sl = ServiceLocator.getServiceLocator();
        tr = sl.getTranslator();

    }










//    public boolean login(String username, String password){
//
//    }
}
