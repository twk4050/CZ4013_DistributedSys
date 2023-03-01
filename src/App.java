import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World From Main.java");

        final int PORT_NO = 1234;

        // Server myServer = new Server(PORT_NO);
        // myServer.startListening();

        FlightOverview flightOverview = new FlightOverview(generateSeedData());

        testRequirement6(flightOverview, 500);
    }

    public static List<Flight> generateSeedData() {
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

    // 1. getFlight(string source,string dest) -> List of Flights/len=0, return err
    public static void testRequirement1(FlightOverview flightOverview, String source, String dest) {
        System.out.println("testing requirement1 getFlight by source/dest");
        List<Flight> flights = flightOverview.getFlight(source, dest);
        for (Flight f : flights) {
            System.out.println(f);
        }
    }

    // 2. GetFlightById(int flight_id) -> flight details / error message
    public static void testRequirement2(FlightOverview flightOverview, int flightId) {
        System.out.println("testing requirement2 getFlightById");
        Flight f = flightOverview.getFlightById(flightId);
        System.out.println(f);
    }

    // 3. reserveSeat(int flight_id, int num_seat_to_reserve) -> return
    // acknoledgement / return error message(wrong id/noseat)
    public static void testRequirement3(FlightOverview flightOverview, int flightId, int numberOfSeats,
            SClient client) {

        System.out.println("testing requirement3 reserveSeats");
        Response123 response123 = flightOverview.reserveSeat(flightId, numberOfSeats, client);
        System.out.println(response123);
    }

    // 4. monitorFlight(int flight_id, int duration_to_monitor)
    public static void testRequirement4(FlightOverview flightOverview, int flightId, SClient client) {
        System.out.println("testing requirement4 monitorFlights");
        Response123 response123 = flightOverview.monitorFlight(flightId, client);
        System.out.println(response123);
    }

    // 5. cancelSeats() ?

    // 6. getFlightsBelowPrice()
    public static void testRequirement6(FlightOverview flightOverview, float priceThreshold) {
        System.out.println("testing requirement6 getFlightsBelowCertainPrice");
        List<Flight> flights = flightOverview.getFlightBelowCertainPrice(priceThreshold);
        for (Flight f : flights) {
            System.out.println(f);
        }
    }
}
