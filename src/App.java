import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World From Main.java");

        final int PORT_NO = 1234;
        FlightOverview fOverview = new FlightOverview(generateFlightsData());
        SClientOverview clientOverview = new SClientOverview(generateClientsData());

        Server myServer = new Server(PORT_NO, fOverview, clientOverview); // rename FlightServer
        myServer.testStartListening();

        /** test cases */
        // FlightOverview fOverview = new FlightOverview(generateSeedData());

        // InetAddress local = InetAddress.getLocalHost();
        // int client1_port_no = 5555;
        // SClient client1 = new SClient(local, client1_port_no, 300);

        // int client2_port_no = 6666;
        // SClient client2 = new SClient(local, client2_port_no, 60);

        // int flightId = 101;
        // testRequirement3(fOverview, flightId, 3, client1);
        // testRequirement4(fOverview, flightId, client2);
        // testRequirement2(fOverview, flightId);
        // testRequirement3(fOverview, flightId, 3, client1);
        // testRequirement6(fOverview, 500);

        // String s = "59178:1|101|102";
        // String[] list123 = s.split(":");
        // System.out.println(list123);

        // String[] args = s.split("|");
        // for (String x : list123) {
        // System.out.println(x);
        // }
        // for (String x : args) {
        // System.out.println(x);
        // }

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

    /**
     * 1. getFlight(string source,string dest) -> List of Flights/len=0, return err
     */
    // public static void testRequirement1(FlightOverview fOverview, String source,
    // String dest) {
    // System.out.println("testing requirement1 getFlight by source/dest");
    // List<Flight> flights = fOverview.getFlight(source, dest);
    // for (Flight f : flights) {
    // System.out.println(f);
    // }
    // }

    // /** 2. GetFlightById(int flight_id) -> flight details / error message */
    // public static void testRequirement2(FlightOverview fOverview, int flightId) {
    // System.out.println("testing requirement2 getFlightById");
    // Flight f = fOverview.getFlightById(flightId);
    // System.out.println(f);
    // }

    // /**
    // * 3. reserveSeat(int flight_id, int num_seat_to_reserve) -> return
    // * acknoledgement / return error message(wrong id/noseat)
    // */
    // public static void testRequirement3(FlightOverview fOverview, int flightId,
    // int numberOfSeats,
    // SClient client) {

    // System.out.println("testing requirement3 reserveSeats");
    // Response123 response123 = fOverview.reserveSeat(flightId, numberOfSeats,
    // client);
    // System.out.println(response123);
    // System.out.println("seats reserved:" + response123.getSeatsReserved());
    // System.out.println("current monitor list" + response123.getMonitorList());
    // }

    // /** 4. monitorFlight(int flight_id, int duration_to_monitor) */
    // public static void testRequirement4(FlightOverview fOverview, int flightId,
    // SClient client) {
    // System.out.println("testing requirement4 monitorFlights");
    // Response123 response123 = fOverview.monitorFlight(flightId, client);
    // System.out.println(response123);
    // System.out.println(response123.getStatus());
    // System.out.println(response123.getMonitorList());
    // System.out.println(response123.getMonitorList().get(0).getPortNo());
    // }

    // // 5. cancelSeats() ?

    // // 6. getFlightsBelowPrice()
    // public static void testRequirement6(FlightOverview fOverview, float
    // priceThreshold) {
    // System.out.println("testing requirement6 getFlightsBelowCertainPrice");
    // List<Flight> flights = fOverview.getFlightBelowCertainPrice(priceThreshold);
    // for (Flight f : flights) {
    // System.out.println(f);
    // }
    // }
}
