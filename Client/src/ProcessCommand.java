import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Command
 */
public class ProcessCommand {

  private String processedCommand;

  public String getProcessedCommand() {
    return processedCommand;
  }

  public void process(String command) {
    String[] tokens = command.split(" ");
    String action = tokens[0];

    switch (action) {
      case "SET":
        setCommand(command);
        break;
      case "GET":
        getCommand(command);
        break;
      case "DELETE":
        deleteCommand(command);
        break;
      case "COUNT":
        countCommand(command);
        break;
      case "TTL":
        ttlCommand(command);
        break;
      default:
        throw new IllegalArgumentException("Invalid command");
    }
  }

  public void setCommand(String command) {
    Pattern pattern = Pattern.compile("'([^']*)'|\"([^\"]*)\"|(\\S+)");
    Matcher matcher = pattern.matcher(command);

    List<String> tokens = new ArrayList<>();
    while (matcher.find()) {
      if (matcher.group(1) != null) {
        tokens.add(matcher.group(1));
      } else if (matcher.group(2) != null) {
        tokens.add(matcher.group(2));
      } else {
        tokens.add(matcher.group(3));
      }
    }

    this.processedCommand = tokens.toString();
  }

  public void getCommand(String command) {
    String[] tokens = command.split(" ");
    if (tokens.length != 2 || !tokens[1].matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
      throw new IllegalArgumentException("Invalid command");
    }

    this.processedCommand = "[GET, " + tokens[1].trim() + "]";
  }

  public void deleteCommand(String command) {
    String[] tokens = command.split(" ");
    if (tokens.length != 2 || !tokens[1].matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
      throw new IllegalArgumentException("Invalid command");
    }

    this.processedCommand = "[DELETE, " + tokens[1].trim() + "]";
  }

  public void countCommand(String command) {
    String[] tokens = command.split(" ");
    if (tokens.length != 1) {
      throw new IllegalArgumentException("Invalid command");
    }

    this.processedCommand = "[COUNT]";
  }

  public void ttlCommand(String command) {
    String[] tokens = command.split(" ");
    if (tokens.length != 4 || !tokens[2].equals("-E") || !tokens[3].matches("\\d+")
        || !tokens[1].matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
      throw new IllegalArgumentException("Invalid command");
    }

    this.processedCommand = "[TTL, " + tokens[1].trim() + ", -E, " + tokens[3].trim() + "]";
  }
}
