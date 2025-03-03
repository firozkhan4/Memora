import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientHandler extends Thread {
  private final Socket clientSocket;
  private final InMemoryDB db; 

  public ClientHandler(Socket socket) {
    this.clientSocket = socket;
    this.db = InMemoryDB.getInstance();
  }

  @Override
  public void run() {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {

      String clientMessage;
      while ((clientMessage = reader.readLine()) != null) {
        System.out.println("Received: " + clientMessage);
        writer.println("Server: Message Received");
      }
    } catch (IOException e) {
      System.err.println("Error handling client connection: " + e.getMessage());
    } finally {
      try {
        clientSocket.close();
        System.out.println("Client disconnected");
      } catch (IOException e) {
        System.err.println("Error closing client socket: " + e.getMessage());
      }
    }
  }

  private void handleClientMessage(String command) {
    String[] parts = command.split(" ");
    String action = parts[0];

    if(action.equals("SET")){

    }
  }
}
