import java.io.*;
import java.net.*;
//ClientHandler handles one single client in the chat server.
public class ClientHandler implements Runnable { //it can run inside a thread.
    private Socket socket;
    private BufferedReader input; //to read data from the client
    private PrintWriter output; //to send data to the client
    private String username;
    
    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.output = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            closeEverything();
        }
    }
    
    @Override
    public void run() {
        try {
            username = input.readLine();
            System.out.println("[SERVER] " + username + " has joined the chat!");
            Server.broadcastMessage(username + " has joined the chat!", this);
            
            String message;
            while ((message = input.readLine()) != null) {   //waits until: The client sends a line of text Or the client disconnects
                System.out.println("[SERVER] Received from " + username + ": " + message);
                Server.broadcastMessage(username + ": " + message, this);
            }
            
        } catch (IOException e) {
            System.out.println("[SERVER] Error with " + username + ": " + e.getMessage());
            closeEverything();
        } finally {
            closeEverything();
        }
    }
    
    public void sendMessage(String message) {
        if (output != null) {
            System.out.println("[SERVER] Sending to " + username + ": " + message);
            output.println(message);
            output.flush();
        }
    }
    
    private void closeEverything() {
        Server.removeClient(this);
        try {
            if (input != null) input.close();
            if (output != null) output.close();
            if (socket != null) socket.close();
            if (username != null) {
                System.out.println(username + " has left the chat.");
                Server.broadcastMessage(username + " has left the chat.", this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
