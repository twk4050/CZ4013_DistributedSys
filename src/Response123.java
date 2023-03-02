import java.util.List;

public class Response123 {
    private String status;
    private String message;
    private List<Integer> seatsReserved;
    private List<SClient> monitorList;
    private List<Flight> flights;
    private Flight flight;

    final String OK_STATUS = "1";
    final String ERROR_STATUS = "-1";

    // constructor1
    public Response123(String status, String message) {
        this.status = status;
        this.message = message;
    }

    // constructor 2
    public Response123(String status, List<Integer> seatsReserved, List<SClient> monitorList, Flight flight) {
        this.status = status;
        this.seatsReserved = seatsReserved;
        this.monitorList = monitorList;
        this.flight = flight;
    }

    // constructor 3
    public Response123(String status, List<SClient> monitorList) {
        this.status = status;
        this.monitorList = monitorList;
    }

    // constructor 4
    public Response123(String status, List<Flight> flights, String empty) {
        this.status = status;
        this.flights = flights;
    }

    public String getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }

    public List<Integer> getSeatsReserved() {
        return this.seatsReserved;
    }

    public List<SClient> getMonitorList() {
        return this.monitorList;
    }

    public List<Flight> getFlights() {
        return this.flights;
    }

    public Flight getFlight() {
        return this.flight;
    }
}
