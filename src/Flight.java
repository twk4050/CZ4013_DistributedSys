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

    public int totalSeats;

    public Flight(int flightId, String source, String dest, int departTime, float airfare, int totalSeats) {
        this.flightId = flightId;
        this.source = source;
        this.dest = dest;
        this.departTime = departTime;
        this.airfare = airfare;
        this.totalSeats = 250;
        this.seatsLeft = this.totalSeats;
        this.seatMapping = new HashMap<>();
        this.monitorList = new ArrayList<>();

        for (int i = 0; i < this.totalSeats; i++) {
            this.seatMapping.put(i, -1); // initialize seat0 -> -1 ... seat249 -> -1
        }
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

    public ArrayList<Integer> getMonitorList() {
        return this.monitorList;
    }

    public int mapSeats(int personId) {

        for (int seatNumber = 0; seatNumber < this.totalSeats; seatNumber++) {
            int currentPersonId = this.seatMapping.get(seatNumber);

            // if its empty, assign this seat to the person
            if (currentPersonId == -1) {
                this.seatMapping.put(seatNumber, personId);
                this.seatsLeft -= 1; // change to this.setSeatsLeft() ?

                return seatNumber;
            }
        }

        return -1; // if all seats are taken
    }

    public void cancelAllSeatsForPerson(int personId) {
        for (int seatNumber = 0; seatNumber < this.totalSeats; seatNumber++) {
            int currentPersonId = this.seatMapping.get(seatNumber);

            if (currentPersonId == personId) {
                this.seatMapping.put(seatNumber, -1);
                this.seatsLeft += 1;

            }
        }
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
