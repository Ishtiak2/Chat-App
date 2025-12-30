import java.io.*;
import java.net.*;

public class Client {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private ClientGUI gui;
    
    public Client(String serverAddress, int port, String username, ClientGUI gui) {
        this.gui = gui;
        try {
            socket = new Socket(serverAddress, port);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream())); // read message from server
            output = new PrintWriter(socket.getOutputStream(), true); // send message to server
            
            output.println(username); //sends the user name
            
            new Thread(new MessageListener()).start(); //start listening for messages from server
            
        } catch (IOException e) {
            gui.displayMessage("Error connecting to server: " + e.getMessage());
            closeEverything();
        }
    }
    
    public void sendMessage(String message) {
        if (output != null) {
            System.out.println("[CLIENT] Sending message: " + message);
            output.println(message);
            output.flush();
        }
    }
    
    private void closeEverything() {
        try {
            if (input != null) input.close();
            if (output != null) output.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //This is a background thread that constantly listens for messages from server.
    private class MessageListener implements Runnable {
        @Override
        public void run() {
            String message;
            try {
                while ((message = input.readLine()) != null) {
                    System.out.println("[CLIENT] Received: " + message);
                    gui.displayMessage(message);
                }
            } catch (IOException e) {
                gui.displayMessage("Connection lost.");
                closeEverything();
            }
        }
    }
}
