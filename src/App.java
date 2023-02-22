public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World From Main.java");

        final int PORT_NO = 1234;

        Server myServer = new Server(PORT_NO);
        myServer.startListening();
    }
}
