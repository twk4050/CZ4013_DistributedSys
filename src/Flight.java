import java.util.ArrayList;
import java.util.HashMap;

public class Flight {
    public int flightId;
    public String source;
    public String dest;
    public int departTime;
    public float airfare;
    public int seatsLeft; // AvailableSeats
    public HashMap<Integer, Integer> seatMapping;
    // seat 0 -> id100
    // seat 101 -> id123
    public ArrayList<Integer> monitorList;

    public Flight(int flightId, String source, String dest, int departTime, float airfare, int seatsLeft) {
        this.flightId = flightId;
        this.source = source;
        this.dest = dest;
        this.departTime = departTime;
        this.airfare = airfare;
        this.seatsLeft = seatsLeft;
        this.seatMapping = new HashMap<>();
        this.monitorList = new ArrayList<>();
    }

    public Integer getId() {
        return this.flightId;
    }

    public String getSource() {
        return this.source;
    }

    public String getDest() {
        return this.dest;
    }

    public int getDepartTime() {
        return this.departTime;
    }

    public float getAirfare() {
        return this.airfare;
    }

    public int getSeatsLeft() {
        return this.seatsLeft;
    }

    public void setSeatsLeft(int seatsLeft) {
        this.seatsLeft = seatsLeft;
    }

    public ArrayList<Integer> getMonitorList() {
        return this.monitorList;
    }

    public void cancelSeats(int personId) {

    }

    public void adjustmonitorList() {
        // add or remove (list);
    }

    // public void addToMonitorList(int personId){
    // this.monitorList
    // }
    // Singapore -> Kuala Lumpur
    // f.source = Singapore
    // f.dest = Kuala Lumpur

}
