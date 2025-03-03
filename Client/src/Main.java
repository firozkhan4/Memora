class Main {
  public static void main(String[] args) {
    InMemoryDBClient client = new InMemoryDBClient("localhost", 8080);
    client.start();
  }
}
