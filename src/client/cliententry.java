package client;

import entity.message;

public class cliententry {
    public String userName="";
    public String passWord="";
    public int flightId='0';
    public String source= "";
    public String destination="";
    public int seatToReserve= 0 ;
    public float priceThreshold= 0.00f;
    public double interval= 0.00;
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

    // need to account for account details later
    public message reserveSeats(){
        System.out.println("\nPlease enter the flight ID: ");
        flightId = readuserinput.inputInt();
        System.out.println("\nPlease enter the number of seats to be reserved: ");
        seatToReserve = readuserinput.inputInt();
        System.out.println("\nPlease enter Username: ");
        userName = readuserinput.inputStr();
        System.out.println("\nPlease enter Password: ");
        passWord = readuserinput.inputPass();
        message msg = client.reserveSeats(flightId,seatToReserve,userName,passWord);
        return msg;
    }
    // might remove username and password for this later becos not necessary
    public message monitorflight(){
        System.out.println("\nPlease enter the flight ID: ");
        flightId = readuserinput.inputInt();
        System.out.println("\nPlease enter interval: ");
        interval = readuserinput.inputDouble();
        message msg = client.monitorflight(flightId, userName, passWord, interval);
        return msg;
    }
    public message cancelSeat(){
        System.out.println("\nPlease enter the flight ID: ");
        flightId = readuserinput.inputInt();
        System.out.println("\nPlease enter Username: ");
        userName = readuserinput.inputStr();
        System.out.println("\nPlease enter Password: ");
        passWord = readuserinput.inputPass();
        message msg = client.cancelSeat(flightId, userName, passWord);
        return msg;
    }
    public message getFlightBelowCertainPrice(){
        System.out.println("\nPlease enter the maximum price threshold: ");
        priceThreshold = readuserinput.inputFloat();
        message msg = client.getFlightBelowCertainPrice(priceThreshold);
        return msg;
    }
}
