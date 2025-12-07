import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClientGUI extends JFrame {
    private JTextArea messageArea;
    private JTextField messageInput;
    private JButton sendButton;
    private Client client;
    
    private JTextField usernameField;
    private JTextField ipField;
    private JTextField portField;
    private JButton connectButton;
    private JPanel connectPanel;
    private JPanel chatPanel;
    private String username;
    
    public ClientGUI() {
        setTitle("Chat Application");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        createConnectPanel();
        createChatPanel();
        
        add(connectPanel, BorderLayout.NORTH);
        add(chatPanel, BorderLayout.CENTER);
        
        chatPanel.setVisible(false);
        
        setVisible(true);
    }
    
    private void createConnectPanel() {
        connectPanel = new JPanel();
        connectPanel.setLayout(new GridLayout(4, 2, 5, 5));
        connectPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        connectPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        connectPanel.add(usernameField);
        
        connectPanel.add(new JLabel("Server IP:"));
        ipField = new JTextField("localhost");
        connectPanel.add(ipField);
        
        connectPanel.add(new JLabel("Port:"));
        portField = new JTextField("5555");
        connectPanel.add(portField);
        
        connectButton = new JButton("Connect");
        connectButton.addActionListener(e -> connectToServer());
        connectPanel.add(new JLabel());
        connectPanel.add(connectButton);
    }
    
    private void createChatPanel() {
        chatPanel = new JPanel();
        chatPanel.setLayout(new BorderLayout());
        
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        chatPanel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout(5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        messageInput = new JTextField();
        messageInput.addActionListener(e -> sendMessage());
        inputPanel.add(messageInput, BorderLayout.CENTER);
        
        sendButton = new JButton("Send");
        sendButton.addActionListener(e -> sendMessage());
        inputPanel.add(sendButton, BorderLayout.EAST);
        
        chatPanel.add(inputPanel, BorderLayout.SOUTH);
    }
    
    private void connectToServer() {
        String username = usernameField.getText().trim();
        String ip = ipField.getText().trim();
        String portText = portField.getText().trim();
        
        if (username.isEmpty() || ip.isEmpty() || portText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            int port = Integer.parseInt(portText);
            this.username = username;
            client = new Client(ip, port, username, this);
            
            connectPanel.setVisible(false);
            chatPanel.setVisible(true);
            setTitle("Chat Application - " + username);
            messageInput.requestFocus();
            
            displayMessage("Connected to server!");
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid port number!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void sendMessage() {
        String message = messageInput.getText().trim();
        if (!message.isEmpty() && client != null) {
            System.out.println("[GUI] User sending: " + message);
            client.sendMessage(message);
            messageInput.setText("");
        }
    }
    
    public void displayMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            messageArea.append(message + "\n");
            messageArea.setCaretPosition(messageArea.getDocument().getLength());
        });
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClientGUI());
    }
}
