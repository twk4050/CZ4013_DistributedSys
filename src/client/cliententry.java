package client;

import entity.message;

public class cliententry {
    public String userName="";
    public String passWord="";
    public int flightId='0';
    public String source= "";
    public String destination="";
    public int seatToReserve= 0 ;
    private clientmessage client = null;

    public cliententry(clientmessage c) {
		client = c;
	}

    public message queryFlightId(){
        System.out.println("\nPlease enter the source of the flight: ");
        source = readuserinput.inputStr();
        System.out.println("\nPlease enter the destination of the flight: ");
        destination = readuserinput.inputStr();
        message msg = client.queryFlightId(source,destination);
        return msg;
    }

    public message queryFlightInfo(){
        System.out.println("\nPlease enter the flight ID: ");
        flightId = readuserinput.inputInt();
        message msg = client.queryFlightInfo(flightId);
        return msg;
    }

    public message reserveSeats(){
        System.out.println("\nPlease enter the flight ID: ");
        flightId = readuserinput.inputInt();
        System.out.println("\nPlease enter the number of seats to be reserved: ");
        seatToReserve = readuserinput.inputInt();

        message msg = client.reserveSeats(flightId,seatToReserve);
        return msg;
    }
}
