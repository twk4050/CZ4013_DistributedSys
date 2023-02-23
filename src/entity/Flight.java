package models;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Flight {
    public Integer flightId;
    public String flightSource;
    public String flightDestination;

    // Still unsure of the format of the datetime
    public DateTimeFormatter departureTime;
    public Float airfare;
    public Integer availableSeats;

    public Flight(Integer flightId, String flightSource, String flightDestination, DateTimeFormatter departureTime,
            Float airfare, Integer availableSeats) {
        this.flightId = flightId;
        this.flightSource = flightSource;
        this.flightDestination = flightDestination;
        this.departureTime = departureTime;
        this.airfare = airfare;
        this.availableSeats = availableSeats;
    }

    public static List<Flight> flightList = new ArrayList<Flight>();

    public Integer getFlightId() {
        return flightId;
    }

    public void setFlightId(Integer flightId) {
        this.flightId = flightId;
    }

    public String getFlightSource() {
        return flightSource;
    }

    public void setFlightSource(String flightSource) {
        this.flightSource = flightSource;
    }

    public String getFlightDestination() {
        return flightDestination;
    }

    public void setFlightDestination(String flightDestination) {
        this.flightDestination = flightDestination;
    }

    public DateTimeFormatter getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(DateTimeFormatter departureTime) {
        this.departureTime = departureTime;
    }

    public Float getAirfare() {
        return airfare;
    }

    public void setAirfare(Float airfare) {
        this.airfare = airfare;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public void setFlightList(List<Flight> flightList) {
        Flight.flightList = flightList;
    }

    public void addFlight(Flight flight) {
        flightList.add(flight);
    }

    public List<Flight> getFlightList() {
        return flightList;
    }
}
