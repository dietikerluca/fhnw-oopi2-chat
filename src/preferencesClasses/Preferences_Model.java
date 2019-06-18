package src.preferencesClasses;

import src.abstractClasses.Model;

import java.util.regex.Pattern;

public class Preferences_Model extends Model {


    public Preferences_Model(){
        super();
    }

    /* Partly Copied from Bradley Richards
     * -----------------------------------------------------------------------------------*/
    public static boolean validateIpAddress(String ipAddress) {
        boolean formatOK = true;
        boolean numbersOnly = true;
        String ipPieces[] = ipAddress.split("\\.");
        for (String piece : ipPieces) {
            if (Pattern.matches("[a-zA-Z]+", piece)) {
                numbersOnly = false;
            }
        }
        if (numbersOnly) {
            if (ipPieces.length != 4) {
                formatOK = false;
            } else {
                // Each part must be an integer 0 to 255
                int byteValue = -1;
                for (String s : ipPieces) {
                    byteValue = Integer.parseInt(s); // may throw
                    // NumberFormatException
                    if (byteValue < 0 | byteValue > 255) formatOK = false;
                }
            }
        }
        return (formatOK && numbersOnly);
    }

    public static boolean validatePortNumber(String portText) {
        boolean formatOK = false;
        try {
            int portNumber = Integer.parseInt(portText);
            if (portNumber >= 0 & portNumber <= 65535) {
                formatOK = true;
            }
        } catch (NumberFormatException e) {
        }
        return formatOK;
    }


}
