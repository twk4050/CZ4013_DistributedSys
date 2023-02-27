import java.util.ArrayList;
import java.util.List;

public class FlightOverview {
    public List<Flight> flights;

    public FlightOverview(List<Flight> flights) {
        this.flights = flights;
    }

    // 1. getFlight(string source, string dest) -> List of Flights / if length == 0
    // error message
    public List<Flight> getFlight(String source, String dest) {

        List<Flight> flightList = new ArrayList<Flight>();

        source = source.toLowerCase();
        dest = dest.toLowerCase();

        for (Flight f : this.flights) {
            if (f.getSource().equals(source) && f.getDest().equals(dest)) {
                flightList.add(f);
            }
        }

        return flightList;
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
    // public Boolean reserveSeat(int flightId, int numberOfSeats, int personId){

    // Flight f = getFlightById(flightId);

    // if (f == null){
    // return false;
    // }

    // if (f.getSeatsLeft() < numberOfSeats){
    // return false;
    // }

    // /* handle in Flight.java */
    // int counter = f.seatMapping.size(); // 0

    // for (int i = 0; i < numberOfSeats; i++){
    // int seatNum = counter + i;
    // f.seatMapping.put(seatNum, personId);
    // }

    // int seatsLeft = f.getSeatsLeft() - numberOfSeats);
    // f.setSeatsLeft(seatsLeft);

    // // f.adjustmonitorList();
    // // return null
    // // return Object { status: success, seatsReserved: [101,102,103],
    // monitorList: [p1,p2,p3] }
    // return true; // return monitorList = [personid10, ]
    // }

    // 4. monitorFlight(int flight_id, int duration_to_monitor)
    // public void monitorFlight(int flightId, int durationToMonitor) {
    // Flight f = getFlightById(flightId);

    // // TODO: handle error if f is null
    // // Person class = personId, username, pw, personIP, personPort
    // f.monitorList.add(personId);

    // }

    // // 5. 1 idempotent request `cancel reserved seat`
    // public Boolean cancelSeat(int flightId, int personId) {
    // Flight f = getFlightById(flightId);

    // }

    // 6. 1 non idempotent request `find flight below x airfare`

    public List<Flight> getFlightBelowCertainPrice(float priceThreshold) {
        List<Flight> flightList = new ArrayList<Flight>();

        for (Flight f : this.flights) {
            if (f.getAirfare() < priceThreshold) {
                flightList.add(f);
            }
        }

        return flightList;
    }

}
