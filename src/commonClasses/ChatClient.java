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

    public ChatClient(String hostAddress, int hostPort) {
        this.hostAddress = hostAddress;
        this.hostPort = hostPort;

        incomingMessages = FXCollections.observableArrayList();
        sl = ServiceLocator.getServiceLocator();
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

                return true;
            } catch (Exception e) {
                e.printStackTrace();

                return false;
            }
        }
        return true;
    }

    private ChatCommand sendCommand(String commandString) {
        ChatCommand command = new ChatCommand(commandString, socketOut);
        try {
            commandQueue.put(command);
            command.execute();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

        System.out.println(command);
        while (!command.isFinished()) {
            Thread.yield();
        }
        return command;
    }

    public ChatCommand ping() {
        if (token != null) {
            return sendCommand("Ping|" + token);
        } else {
            return sendCommand("Ping");
        }
    }

    public boolean createLogin(String username, String password) {
        String[] response = sendCommand("CreateLogin|" + username + "|" + password).parseReturnValue();
        return response[1].equals("true");
    }

    public boolean login(String username, String password) {
        String[] response = sendCommand("Login|" + username + "|" + password).parseReturnValue();

        if (response[1].equals("true")) {
            token = response[2];
            return true;
        } else {
            return false;
        }
    }

    public boolean changePassword(String newPassword) {
        String[] response = sendCommand("ChangePassword|"+ token + "|" + newPassword).parseReturnValue();

        if (response[1].equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteAccount() {
        String[] response = sendCommand("DeleteLogin|"+ token).parseReturnValue();

        if (response[1].equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean logout() {
        if (token != null) {
            sendCommand("Logout");
        }
        return true;
    }

    public boolean createChatroom(String name, boolean isPublic) {
        if (token != null) {
            String[] response = sendCommand("CreateChatroom|" + token + "|" + name + "|" + isPublic).parseReturnValue();
            return response[1].equals("true");
        }
        return false;
    }

    public boolean joinChatroom(String chatroom, String user) {
        if (token != null) {
            String[] response = sendCommand("JoinChatroom|" + token + "|" + chatroom + "|" + user).parseReturnValue();
            return response[1].equals("true");
        }
        return false;
    }

    public boolean leaveChatroom(String chatroom, String user) {
        if (token != null) {
            String[] response = sendCommand("LeaveChatroom|" + token + "|" + chatroom + "|" + user).parseReturnValue();
            return response[1].equals("true");
        }
        return false;
    }

    public boolean deleteChatroom(String chatroom) {
        if (token != null) {
            String[] response = sendCommand("DeleteChatroom|" + token + "|" + chatroom).parseReturnValue();
            return response[1].equals("true");
        }
        return false;
    }

    public String[] listChatrooms() {
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

    public boolean sendMessage(String target, String message) {
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
