package src.commonClasses;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class ChatCommand {
    private String command;
    private String returnValue;
    private boolean finished;

    private OutputStreamWriter socket;

    public ChatCommand(String command, OutputStreamWriter socket) {
        this.command = command;
        this.socket = socket;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public String getCommand() {
        return command;
    }

    public String getReturnValue() {
        return returnValue;
    }

    public String[] parseReturnValue() {
        String[] responseArguments = returnValue.split("\\|");
        return responseArguments;
    }

    public boolean isFinished() {
        return finished;
    }

    public void execute() throws IOException {
        socket.write(command + "\n");
        socket.flush();
    }

    public String toString() {
        if (finished) {
            return "Command: " + command + " Returned: " + returnValue;
        } else {
            return "Command: " + command + " Pending...";
        }
    }
}
