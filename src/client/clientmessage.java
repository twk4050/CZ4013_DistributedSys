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
        strng.append(split);
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
        strng.append(split);
        String request = strng.toString();
        msg.setMessageContent(request);
        return msg;
    }
    public message reserveSeats(int flightId, int seatToReserve, String username, String password){
        message msg = createRequestMessage();
        StringBuilder strng = new StringBuilder();
        strng.append(3);
        strng.append(split);
        strng.append(flightId);
        strng.append(split);
        strng.append(seatToReserve);
        strng.append(split);
        strng.append(username);
        strng.append(split);
        strng.append(password);
        strng.append(split);
        String request = strng.toString();
        msg.setMessageContent(request);
        return msg;
    }
    public message monitorflight(int flightId, String username, String password, double interval){
        message msg = createRequestMessage();
        StringBuilder strng = new StringBuilder();
        strng.append(5);
        strng.append(split);
        strng.append(flightId);
        strng.append(split);
        strng.append(interval);
        strng.append(split);
        String request = strng.toString();
        msg.setMessageContent(request);
        return msg;
    }
    public message cancelSeat(int flightId, String username, String password){
        message msg = createRequestMessage();
        StringBuilder strng = new StringBuilder();
        strng.append(5);
        strng.append(split);
        strng.append(username);
        strng.append(split);
        strng.append(password);
        strng.append(split);
        String request = strng.toString();
        msg.setMessageContent(request);
        return msg;
    }
    public message getFlightBelowCertainPrice(float priceThreshold){
        message msg = createRequestMessage();
        StringBuilder strng = new StringBuilder();
        strng.append(6);
        strng.append(split);
        strng.append(priceThreshold);
        strng.append("f");
        strng.append(split);
        String request = strng.toString();
        msg.setMessageContent(request);
        return msg;
    }
}
