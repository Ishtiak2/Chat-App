# Java Swing Chat Application

A simple client-server chat application built with Java Swing and socket programming for learning computer networking concepts.

## Project Structure

```
Chat-App/
├── server/
│   ├── Server.java          # Main server (listens on port 5555)
│   └── ClientHandler.java   # Handles each client connection
└── client/
    ├── Client.java          # Client networking logic
    └── ClientGUI.java       # Swing GUI interface
```

## Features

- Multi-client support (multiple clients can connect to one server)
- Simple Java Swing interface
- Username, IP address, and port input
- Real-time message broadcasting
- Join/leave notifications
- Debug logging for troubleshooting

## How to Run

### Step 1: Compile the Code

Open a terminal and navigate to the project directory.

**Compile Server:**
```bash
cd server
javac Server.java ClientHandler.java
```

**Compile Client:**
```bash
cd client
javac Client.java ClientGUI.java
```

### Step 2: Start the Server

Open Terminal 1 and run:
```bash
cd server
java Server
```

You should see:
```
Server started on port 5555
Waiting for clients...
```

### Step 3: Start Client(s)

Open a new terminal for each client (Terminal 2, 3, etc.):
```bash
cd client
java ClientGUI
```

### Step 4: Connect

In each client GUI window:
1. Enter your **Username** (e.g., "Alice", "Bob")
2. Enter **Server IP**: `localhost` (if running on same machine)
3. Enter **Port**: `5555`
4. Click **Connect**

### Step 5: Chat!

- Type messages in the input field at the bottom
- Press Enter or click **Send**
- Messages appear to all connected clients

## Testing with Multiple Clients

1. Start the server once (Terminal 1)
2. Open multiple terminal windows (Terminal 2, 3, 4...)
3. Run `java ClientGUI` in each terminal
4. Connect each client with different usernames
5. Send messages and see them appear in all client windows

## What You'll See

### Server Terminal:
```
Server started on port 5555
Waiting for clients...
[SERVER] Alice has joined the chat!
[SERVER] Bob has joined the chat!
[SERVER] Received from Alice: Hello everyone!
[SERVER] Sending to Alice: Alice: Hello everyone!
[SERVER] Sending to Bob: Alice: Hello everyone!
```

### Client Terminal (Alice):
```
[GUI] User sending: Hello everyone!
[CLIENT] Sending message: Hello everyone!
```

### Client Terminal (Bob):
```
[CLIENT] Received: Alice: Hello everyone!
```

### Client GUI Windows:
- **Alice sees**: `You: Hello everyone!`
- **Bob sees**: `Alice: Hello everyone!`

## How It Works (Networking Concepts)

### Server Side:
- **ServerSocket**: Listens on port 5000 for incoming connections
- **Socket**: Represents each client connection
- **Multithreading**: Each client gets a separate thread (ClientHandler)
- **Broadcasting**: Server forwards messages to all connected clients

### Client Side:
- **Socket**: Connects to server using IP and port
- **Streams**: BufferedReader (input) and PrintWriter (output)
- **Threads**: Separate thread listens for incoming messages from server
- **GUI**: Swing components for user interaction

## Code Components

**Server.java**: 
- Creates ServerSocket on port 5000
- Accepts client connections in a loop
- Maintains list of all connected clients
- Provides broadcast method to send messages to all clients

**ClientHandler.java**:
- Implements Runnable (runs in separate thread)
- Reads messages from one client
- Broadcasts to all other clients
- Handles client disconnection

**Client.java**:
- Connects to server using Socket
- Sends messages to server
- Listens for incoming messages in background thread
- Updates GUI with received messages

**ClientGUI.java**:
- Swing-based user interface
- Connection panel (username, IP, port)
- Chat panel (message area, input field, send button)
- Launches Client when user clicks Connect

## Common Issues

**Connection refused**: Make sure the server is running before starting clients

**Port already in use**: Another program is using port 5555. Either:
- Close that program, OR
- Change the PORT in `server/Server.java` and default port in `client/ClientGUI.java`

**localhost not working**: Try using "127.0.0.1" instead

**Messages not appearing**: 
- Check server terminal for debug logs `[SERVER] Received from...`
- Check client terminal for `[CLIENT] Sending message...` and `[CLIENT] Received...`
- Make sure both clients are connected to the same server

## Learning Points

- **TCP Sockets**: Reliable, connection-oriented communication
- **Client-Server Architecture**: One server, multiple clients
- **Multithreading**: Handling multiple clients simultaneously
- **I/O Streams**: Reading and writing data over network
- **GUI Event Handling**: Responding to user actions in Swing
- **Thread-Safe Collections**: Using CopyOnWriteArrayList for concurrent access
- **Synchronization**: Preventing race conditions in multi-threaded environment

## Port Configuration

Default port: **5555**

To change the port, edit:
1. `server/Server.java` - Line: `private static final int PORT = 5555;`
2. `client/ClientGUI.java` - Line: `portField = new JTextField("5555");`

Then recompile both server and client.
# Chat-App
