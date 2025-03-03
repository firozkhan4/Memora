import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


class Main {
  private static final String HOST = "localhost";
  private static final int PORT = 8080;

  public static void main(String[] args) {

    start();
  }

  public static void start() {
    try (
    Scanner sc = new Scanner(System.in);
        Socket socket = new Socket(HOST, PORT);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);) {

      boolean running = true;
      System.out.println("Client is connected");

      while(running){
        System.out.print("> ");
        String command = sc.nextLine();

        if(command.equals("stop")){
          running = false;
          continue;
        }

        writer.println(command);
        System.out.println(reader.readLine());

      }

      System.out.println("Client shutdown");

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
