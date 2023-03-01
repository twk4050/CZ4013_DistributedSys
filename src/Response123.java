import java.util.List;

public class Response123 {
    private String status;
    private List<Integer> seatsReserved;
    private List<SClient> monitorList;

    // constructor1
    public Response123(String status) {
        this.status = status;
    }

    // constructor 2
    public Response123(String status, List<Integer> seatsReserved, List<SClient> monitorList) {
        this.status = status;
        this.seatsReserved = seatsReserved;
        this.monitorList = monitorList;
    }

    // constructor 3
    public Response123(String status, List<SClient> monitorList) {
        this.status = status;
        this.monitorList = monitorList;
    }

    public String getStatus() {
        return this.status;
    }

    public List<Integer> getSeatsReserved() {
        return this.seatsReserved;
    }

    public List<SClient> getMonitorList() {
        return this.monitorList;
    }
}
