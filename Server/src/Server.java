import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
  private final int port;
  private volatile boolean running = true;
  private ServerSocket serverSocket;
  private final ExecutorService clientPool = Executors.newCachedThreadPool();

  public Server(int port) {
    this.port = port;
  }

  public void start() {

    try {
      serverSocket = new ServerSocket(port);
      System.out.println("Server started on port " + port);

      Runtime.getRuntime().addShutdownHook(new Thread(this::stop));
      while (running) {
        try {
          Socket clientSocket = serverSocket.accept();
          System.out.println("Client connected");
          if (clientSocket != null)
            clientPool.execute(new ClientHandler(clientSocket));
        } catch (IOException e) {

          if (!running) {
            System.out.println("Server is shutting down...");
          } else {
            System.err.println("Error accepting client connection: " + e.getMessage());
          }
        }
      }
    } catch (IOException e) {
      System.err.println("Could not start server on port " + port + ": " + e.getMessage());
    } finally {
      stop();
    }

  }

  private void stop() {
    running = false;
    try {
      if (serverSocket != null && !serverSocket.isClosed()) {
        serverSocket.close();
      }
      clientPool.shutdown(); 
      System.out.println("Server shut down gracefully.");
    } catch (IOException e) {
      System.err.println("Error closing server socket: " + e.getMessage());
    }
  }
}

