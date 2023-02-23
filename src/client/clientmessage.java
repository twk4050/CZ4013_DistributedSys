package client;

import java.util.Random;

import entity.message;
// TODO: add for more functions
public class clientmessage {
    private String split = "|";

    public clientmessage(){}

    public int generateMessageId(){
        Random random = new Random();
        int n = random.nextInt(99999);
        return n;
    }

    public message createRequestMessage(){
        return new message(generateMessageId(),null);
    }

    public message queryFlightId(String source, String destination){
        message msg = createRequestMessage();
        StringBuilder strng = new StringBuilder();
        strng.append(1);
        strng.append(split);
        strng.append(source);
        strng.append(split);
        strng.append(destination);
        String request = strng.toString();
        msg.setMessageContent(request);
        return msg;
    }   
    public message queryFlightInfo(int flightId){
        message msg = createRequestMessage();
        StringBuilder strng = new StringBuilder();
        strng.append(2);
        strng.append(split);
        strng.append(flightId);
        String request = strng.toString();
        msg.setMessageContent(request);
        return msg;
    }
    public message reserveSeats(int flightId, int seatToReserve){
        message msg = createRequestMessage();
        StringBuilder strng = new StringBuilder();
        strng.append(2);
        strng.append(split);
        strng.append(flightId);
        strng.append(split);
        strng.append(seatToReserve);
        String request = strng.toString();
        msg.setMessageContent(request);
        return msg;
    }
}
