import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    private static final int PORT = 5555;
    private static List<ClientHandler> clientHandlers = new CopyOnWriteArrayList<>(); //Every time any thread updates the list, a brand new copy of the list is made.So your reading list is always stable and safe.
    
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);
            System.out.println("Waiting for clients...");
            
            //Accepting Clients (Infinite Loop)
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);
                
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandlers.add(clientHandler);
                
                Thread thread = new Thread(clientHandler); //Each client is handled in its own thread
                thread.start();
            }
            
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    //Sends the message to every connected client
    public static synchronized void broadcastMessage(String message, ClientHandler sender) {
        System.out.println("Broadcasting: " + message + " to " + clientHandlers.size() + " clients");
        for (ClientHandler client : clientHandlers) {
            client.sendMessage(message);
        }
    }
    
    public static void removeClient(ClientHandler clientHandler) {
        clientHandlers.remove(clientHandler);
    }
}
