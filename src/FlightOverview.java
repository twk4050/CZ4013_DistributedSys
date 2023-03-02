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
            String noFlightsMessage = "there are no flights from " + source + " to " + dest;
            return new Response123("-1", noFlightsMessage);
        }

        String flightsMessage = "The following flights fly from " + source + " to " + dest + " \n";
        for (int i = 0; i < matchedFlights.size(); i++) {
            Flight f = matchedFlights.get(i);
            flightsMessage += "Flight " + f.getId();

            // if last index dun append \n
            if (i == matchedFlights.size() - 1) {
                continue;
            }
            flightsMessage += "\n";

        }

        return new Response123("1", flightsMessage);

    }

    // 2. GetFlightById(int flight_id) -> flight details / error message
    public Response123 getFlightById(int flightId) {

        for (Flight f : this.flights) {
            if (f.getId() == flightId) {
                String flightDetailsToReturn = f.toString();

                return new Response123("1", flightDetailsToReturn, f);
            }
        }

        String notFoundMessage = "Flight " + flightId + " not found";
        return new Response123("-1", notFoundMessage);

    }

    // 3. reserveSeat(int flight_id, int num_seat_to_reserve) -> return
    // acknoledgement / return error message(wrong id/noseat)
    /* return List<seatNumber> [101, 102] */
    public Response123 reserveSeat(int flightId, int numberOfSeats, SClient client) {

        Response123 getFlightByIdResponse = getFlightById(flightId);

        Flight f = getFlightByIdResponse.getFlight();

        if (f == null) {
            String notFoundMessage = "Flight " + flightId + " not found";
            return new Response123("-1", notFoundMessage);
        }

        if (f.getSeatsLeft() < numberOfSeats) {
            String notEnoughSeatsMessage = "Not enough seats available " + "Flight" + f.getId() + " has "
                    + f.getSeatsLeft() + " while you requested for " + numberOfSeats;
            return new Response123("-1", notEnoughSeatsMessage);
        }

        List<Integer> seatsReserved = new ArrayList<>(); // store [seat101, seat102, seat103]

        for (int i = 0; i < numberOfSeats; i++) {
            int seatNumberReserved = f.mapSeats(client.getId());
            if (seatNumberReserved == -1) {
                break; // TODO: if seatsleft == 0 -> unable to reserve seats, need to cancel all seats
            }
            seatsReserved.add(seatNumberReserved);
        }

        String successReservationMessage = "You have reserved seats ";
        for (int i = 0; i < seatsReserved.size(); i++) {
            successReservationMessage += seatsReserved.get(i);
            if (i == seatsReserved.size() - 1) {
                successReservationMessage += ".";
                continue;
            }
            successReservationMessage += " ";
        }

        List<SClient> monitorList = f.getMonitorList();

        // You have reserved seats 1 3 13.
        // Response123(string status, stringmessage, monitorlist, f)
        return new Response123("1", successReservationMessage, monitorList, f);
    }

    // 4. monitorFlight(int flight_id, int duration_to_monitor)
    public Response123 monitorFlight(int flightId, SClient client) {
        Response123 getFlightByIdResponse = getFlightById(flightId);

        Flight f = getFlightByIdResponse.getFlight();

        if (f == null) {
            String notFoundMessage = "Flight " + flightId + " not found";
            return new Response123("-1", notFoundMessage);
        }

        f.addPersonToMonitorList(client);

        // Response123(String status, string message)
        String successMonitorMessage = "You are added to Flight" + f.getId() + " monitor list.";
        return new Response123("1", successMonitorMessage);

    }

    // 5. 1 idempotent request `cancel reserved seat`
    public Response123 cancelSeat(int flightId, SClient client) {
        Response123 getFlightByIdResponse = getFlightById(flightId);

        Flight f = getFlightByIdResponse.getFlight();

        if (f == null) {
            String notFoundMessage = "Flight " + flightId + " not found";
            return new Response123("-1", notFoundMessage);
        }

        List<Integer> seatsCancelled = new ArrayList<>(); // TODO: add in seats cancelled
        f.cancelAllSeatsForPerson(client.getId());

        String successCancelSeatsMessage = "Your reserved seats have been cancelled";
        List<SClient> monitorList = f.getMonitorList();

        return new Response123("1", successCancelSeatsMessage, monitorList, f);
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
            String noFlightsMessage = "there are no flights below $" + priceThreshold;
            return new Response123("-1", noFlightsMessage);
        }

        String flightsMessage = "The following flights are under $" + priceThreshold + ":\n";
        for (int i = 0; i < matchedFlights.size(); i++) {
            Flight f = matchedFlights.get(i);
            flightsMessage += "Flight " + f.getId();

            // if last index dun append \n
            if (i == matchedFlights.size() - 1) {
                continue;
            }
            flightsMessage += "\n";
        }
        return new Response123("1", flightsMessage);
    }

}
