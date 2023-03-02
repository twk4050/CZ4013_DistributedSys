import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World From Main.java");

        final int PORT_NO = 1234;
        FlightOverview fOverview = new FlightOverview(generateFlightsData());
        SClientOverview clientOverview = new SClientOverview(generateClientsData());

        Server flightServer = new Server(PORT_NO, fOverview, clientOverview); // rename FlightServer
        flightServer.startListening();

    }

    public static List<Flight> generateFlightsData() {
        Flight f1 = new Flight(101, "sg1", "kl1", 100, 100f, 250);
        Flight f2 = new Flight(102, "sg2", "kl2", 100, 500f, 250);
        Flight f3 = new Flight(103, "sg3", "kl3", 100, 250f, 250);
        Flight f4 = new Flight(104, "sg4", "kl4", 100, 300f, 250);
        Flight f5 = new Flight(105, "sg5", "kl5", 100, 1000f, 250);

        List<Flight> flights = new ArrayList<>();
        flights.add(f1);
        flights.add(f2);
        flights.add(f3);
        flights.add(f4);
        flights.add(f5);

        return flights;
    }

    public static List<SClient> generateClientsData() {
        SClient c1 = new SClient(1, "user1", "pass1");
        SClient c2 = new SClient(2, "user2", "pass2");
        SClient c3 = new SClient(3, "user3", "pass3");
        SClient c4 = new SClient(4, "user4", "pass4");
        SClient c5 = new SClient(5, "user5", "pass5");

        List<SClient> clients = new ArrayList<>();
        clients.add(c1);
        clients.add(c2);
        clients.add(c3);
        clients.add(c4);
        clients.add(c5);

        return clients;
    }
}
