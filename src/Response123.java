import java.util.List;

public class Response123 {
    private String status;
    private String message;

    private Flight flight;
    private List<SClient> monitorList;

    final String OK_STATUS = "1";
    final String ERROR_STATUS = "-1";

    // constructor1
    public Response123(String status, String message) {
        this.status = status;
        this.message = message;
    }

    // constructor2
    public Response123(String status, String message, Flight flight) {
        this.status = status;
        this.message = message;
        this.flight = flight;
    }

    // constructor3
    public Response123(String status, String message, List<SClient> monitorList, Flight flight) {
        this.status = status;
        this.message = message;
        this.monitorList = monitorList;
        this.flight = flight;
    }

    public String getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }

    public Flight getFlight() {
        return this.flight;
    }

    public List<SClient> getMonitorList() {
        return this.monitorList;
    }
}
