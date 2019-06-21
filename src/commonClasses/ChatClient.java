package src.commonClasses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import src.ServiceLocator;
import src.typeClasses.Message;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

public class ChatClient {
    private String hostAddress;
    private int hostPort;

    private Socket socket;
    private DataInputStream socketIn;
    private OutputStreamWriter socketOut;

    private LinkedBlockingQueue<ChatCommand> commandQueue = new LinkedBlockingQueue<>();
    private ObservableList<Message> incomingMessages;

    private String token;

    private ServiceLocator sl;
    private Logger logger;

    public ChatClient(String hostAddress, int hostPort) {
        this.hostAddress = hostAddress;
        this.hostPort = hostPort;

        incomingMessages = FXCollections.observableArrayList();
        sl = ServiceLocator.getServiceLocator();
        logger = sl.getLogger();
    }

    public boolean connect() {
        if (socket == null) {
            try {
                socket = new Socket(hostAddress, hostPort);
                System.out.println("Connected to " + hostAddress + ":" + hostPort);

                socketIn = new DataInputStream(socket.getInputStream());
                socketOut = new OutputStreamWriter(socket.getOutputStream());

                Runnable incomingListener = () -> {
                    while (true) {
                        String msg;
                        try {
                            msg = socketIn.readLine();
                            System.out.println("Received: " + msg);

                            String[] messageArguments = msg.split("\\|");

                            if (messageArguments[0].equals("MessageText")) {
                                incomingMessages.add(
                                    new Message(
                                            messageArguments[1],
                                            messageArguments[2],
                                            messageArguments[3],
                                            true)
                                );
                            } else {
                                try {
                                    ChatCommand command = commandQueue.poll();
                                    command.setReturnValue(msg);
                                    command.setFinished(true);
                                } catch (NullPointerException e) {
                                    sl.getLogger().warning("Got return value but there are no Commands in the queue");
                                }
                            }
                        } catch (IOException e) {
                            break;
                        }
                        if (msg == null) break; // In case the server closes the socket
                    }
                };
                Thread t = new Thread(incomingListener);
                t.start();

                return ping().parseReturnValue()[1].equals("true");
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    public void disconnect() {
        try {
            if (socket != null) {
                socketOut.close();
                socketIn.close();
                socket.close();
                socket = null;
            }
        } catch (IOException e) {
            // We don't care anymore
        }
    }

    private ChatCommand sendCommand(String commandString) throws IOException{
        if (isConnected()) {
            ChatCommand command = new ChatCommand(commandString, socketOut);
            try {
                commandQueue.put(command);
                command.execute();
            } catch (InterruptedException | IOException e) {
                throw new IOException("Unable to send command");
            }

            System.out.println(command);
            while (!command.isFinished() && !command.isTimeout()) {
                Thread.yield();
            }
            if (!command.isTimeout()) {
                return command;
            } else {
                throw new IOException("Socket timeout");
            }
        } else {
            throw new IOException("Not connected to server");
        }
    }

    public ChatCommand ping() throws IOException {
        if (token != null) {
            return sendCommand("Ping|" + token);
        } else {
            return sendCommand("Ping");
        }
    }

    public boolean createLogin(String username, String password) throws IOException {
        String[] response = sendCommand("CreateLogin|" + username + "|" + password).parseReturnValue();
        return response[1].equals("true");
    }

    public boolean login(String username, String password) throws IOException {
        String[] response = sendCommand("Login|" + username + "|" + password).parseReturnValue();

        if (response[1].equals("true")) {
            token = response[2];
            return true;
        } else {
            return false;
        }
    }

    public boolean changePassword(String newPassword) throws IOException {
        String[] response = sendCommand("ChangePassword|"+ token + "|" + newPassword).parseReturnValue();

        if (response[1].equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteAccount() throws IOException {
        String[] response = sendCommand("DeleteLogin|"+ token).parseReturnValue();

        if (response[1].equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean logout() throws IOException {
        if (token != null) {
            sendCommand("Logout");
        }
        return true;
    }

    public boolean createChatroom(String name, boolean isPublic) throws IOException {
        if (token != null) {
            String[] response = sendCommand("CreateChatroom|" + token + "|" + name + "|" + isPublic).parseReturnValue();
            return response[1].equals("true");
        }
        return false;
    }

    public boolean joinChatroom(String chatroom, String user) throws IOException {
        if (token != null) {
            String[] response = sendCommand("JoinChatroom|" + token + "|" + chatroom + "|" + user).parseReturnValue();
            return response[1].equals("true");
        }
        return false;
    }

    public boolean leaveChatroom(String chatroom, String user) throws IOException {
        if (token != null) {
            String[] response = sendCommand("LeaveChatroom|" + token + "|" + chatroom + "|" + user).parseReturnValue();
            return response[1].equals("true");
        }
        return false;
    }

    public boolean deleteChatroom(String chatroom) throws IOException {
        if (token != null) {
            String[] response = sendCommand("DeleteChatroom|" + token + "|" + chatroom).parseReturnValue();
            return response[1].equals("true");
        }
        return false;
    }

    public String[] listChatrooms() throws IOException {
        if (token != null) {
            String[] response = sendCommand("ListChatrooms|" + token).parseReturnValue();
            if (response[1].equals("true")) {
                String[] chatrooms = new String[response.length - 2];
                System.arraycopy(response, 2, chatrooms, 0, response.length - 2);
                return chatrooms;
            }
        }
        return null;
    }

    public boolean sendMessage(String target, String message) throws IOException {
        if (token != null) {
            String[] response = sendCommand("SendMessage|" + token + "|" + target + "|" + message).parseReturnValue();
            return response[1].equals("true");
        }
        return false;
    }

    public boolean isConnected() {
        return socket != null;
    }

    public String getToken() {
        return token;
    }

    public ObservableList<Message> getIncomingMessages() {
        return incomingMessages;
    }
}
