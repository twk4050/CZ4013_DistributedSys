import java.util.ArrayList;
import java.util.List;

public class FlightOverview {
    public List<Flight> flights;

    public FlightOverview(List<Flight> flights) {
        this.flights = flights;
    }

    // 1. getFlight(string source, string dest) -> List of Flights / if length == 0
    // error message
    public Response123 getFlight(String source, String dest) {

        List<Flight> matchedFlights = new ArrayList<Flight>();

        source = source.toLowerCase();
        dest = dest.toLowerCase();

        for (Flight f : this.flights) {
            if (f.getSource().equals(source) && f.getDest().equals(dest)) {
                matchedFlights.add(f);
            }
        }

        if (matchedFlights.size() == 0) {
            return new Response123("there are no flights from " + source + " to " + dest);
        }

        String concatString = "";
        for (Flight f : matchedFlights) {
            concatString += f.getId() + "\n";
        }

        return new Response123(concatString, matchedFlights, "not in use");

    }

    // 2. GetFlightById(int flight_id) -> flight details / error message
    public Flight getFlightById(int flightId) {
        for (Flight f : this.flights) {
            if (f.getId() == flightId) {
                return f;
            }
        }

        return null;
    }

    // 3. reserveSeat(int flight_id, int num_seat_to_reserve) -> return
    // acknoledgement / return error message(wrong id/noseat)
    /* return List<seatNumber> [101, 102] */
    public Response123 reserveSeat(int flightId, int numberOfSeats, SClient client) {

        Flight f = getFlightById(flightId);

        if (f == null) {
            return new Response123("flight with flightid not found");
        }

        if (f.getSeatsLeft() < numberOfSeats) {
            return new Response123("seatsLeft < numberOfSeats");
        }

        List<Integer> seatsReserved = new ArrayList<>(); // store [seat101, seat102, seat103]

        for (int i = 0; i < numberOfSeats; i++) {
            int seatNumberReserved = f.mapSeats(client.getId());
            if (seatNumberReserved == -1) {
                break; // if seatsleft == 0 -> unable to reserve seats, need to cancel all seats
            }
            seatsReserved.add(seatNumberReserved);
        }

        f.removeExpiredFromMonitorList();
        List<SClient> monitorList = f.getMonitorList();

        // return Object { status: success, seatsReserved: [101,102,103],
        return new Response123("success", seatsReserved, monitorList);
    }

    // 4. monitorFlight(int flight_id, int duration_to_monitor)
    public Response123 monitorFlight(int flightId, SClient client) {
        Flight f = getFlightById(flightId);

        if (f == null) {
            return new Response123("flight with flightid not found");
        }

        f.addPersonToMonitorList(client);

        return new Response123("client added to monitorList", f.getMonitorList());

    }

    // 5. 1 idempotent request `cancel reserved seat`
    public Response123 cancelSeat(int flightId, SClient client) {
        Flight f = getFlightById(flightId);

        if (f == null) {
            return new Response123("flight with flightid not found");
        }

        f.cancelAllSeatsForPerson(client.getId());

        return new Response123("all seats cancelled");

    }

    // 6. 1 non idempotent request `find flight below x airfare`

    public Response123 getFlightBelowCertainPrice(float priceThreshold) {
        List<Flight> matchedFlights = new ArrayList<Flight>();

        for (Flight f : this.flights) {
            if (f.getAirfare() < priceThreshold) {
                matchedFlights.add(f);
            }
        }

        if (matchedFlights.size() == 0) {
            return new Response123("there are no flights below $" + priceThreshold);
        }

        String concatString = "";
        for (Flight f : matchedFlights) {
            concatString += f.toString() + "\n";
        }

        return new Response123(concatString, matchedFlights, "not in use");

    }

}
