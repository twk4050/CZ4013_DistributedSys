package client;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

import entity.message;


public class main {
    DatagramSocket socket;
    int serverPortNum;

    public main(int serverPortNum) throws IOException {
        this.socket = new DatagramSocket();
        this.serverPortNum = serverPortNum;
    }

    public void sendPacket(String messageString) throws Exception {
        byte[] buffer = messageString.getBytes();
        InetAddress ip = InetAddress.getLocalHost();

        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, ip, this.serverPortNum);

        this.socket.send(packet);
    }

    public String receivePacket() throws IOException {
        byte[] buffer = new byte[65535];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        this.socket.receive(packet); // blocking

        InetAddress serverAddress = packet.getAddress();
        int serverPort = packet.getPort();
        String messageFromClient = convertByteToStringBuilder(buffer).toString();
        return messageFromClient;
        // String messageFromClient = convertByteToStringBuilder(buffer).toString();
        // System.out.println("From Server" + serverAddress + "/" + serverPort + ": " + messageFromClient);
    }
    public static StringBuilder convertByteToStringBuilder(byte[] a) {
        if (a == null)
            return null;
        StringBuilder ret = new StringBuilder();
        int i = 0;
        while (a[i] != 0) {
            ret.append((char) a[i]);
            i++;
        }
        return ret;
    }
    public static void main(String args[]) throws IOException, Exception {

        main c1 = new main(1234);
        Scanner sc = new Scanner(System.in);
        String menu = 
        "\n----------------------------------------\n" +
        "Distributed Flight Information System\n" +
        "----------------------------------------\n" +
        "Select an option:\n" +
        "1. Find Flight ID by Source and Destination\n" +
        "2. Find Flight Information By ID\n" +
        "3. Reserve a Flight\n" +
        "4. Monitor Seat Availability\n" +
        "5. Cancel Reservation\n" +
        "6. Find flights below certain price\n" +
        "7. Exit\n" +
        "----------------------------------------\n";
        
        System.out.print(menu);
        clientmessage c = new clientmessage();
        cliententry e = new cliententry(c);
        // need to resend for at least once later on
        while (true) {
            System.out.print("Please enter your selection:");
            int userinput = readuserinput.inputInt();
            message m = null;
            String result = null;
            String[] results = null;
            String msg = null;

            int count = 0;
            int retry = 5;
            int timeout = 3000;
            
            switch(userinput){
                case 1:
                    m = e.queryFlightId();
                    msg = m.messageToString();
                    String response1 = null;

                    while (count < retry) {
                        try{
                            c1.socket.setSoTimeout(timeout);
                            c1.sendPacket(msg);
                            response1 = c1.receivePacket();
                            if (!response1.equals(null)){
                                count = 0;
                                break;
                            }
                        }
                        catch (Exception e1) {
                            System.out.println("Timeout, retrying...");
                        }
                        
                        count++;
                    }
                    if (response1.equals(null)){
                        System.out.println("Server is not responding, please try again later.");
                    }
                    else{
                        System.out.println(response1);
                    }
                    break;
                case 2:
                    m = e.queryFlightInfo();
                    msg = m.messageToString();
                    c1.sendPacket(msg);
                    String response2 = c1.receivePacket();
                    System.out.println(response2);
                    break;
                case 3:
                    m = e.reserveSeats();
                    msg = m.messageToString();
                    c1.sendPacket(msg);
                    String response3 = c1.receivePacket();
                    System.out.println(response3);
                    break;
                case 4:
                    m = e.monitorflight();
                    msg = m.messageToString();
                    c1.sendPacket(msg);
                    String responsefromServer = c1.receivePacket();
                    System.out.println(responsefromServer);
                    if (responsefromServer.contains("added")) {
                        while (true) {
                            String monitorResponse = c1.receivePacket();
                            System.out.println(monitorResponse);
                            if (monitorResponse.contains("removed")) {
                                break;
                            }
                        }
                    }
                    break;
                case 5:
                    m = e.cancelSeat();
                    msg = m.messageToString();
                    c1.sendPacket(msg);
                    String response5 = c1.receivePacket();
                    System.out.println(response5);
                    break;
                case 6:
                    m = e.getFlightBelowCertainPrice();
                    msg = m.messageToString();
                    c1.sendPacket(msg);
                    String response6 = c1.receivePacket();
                    System.out.println(response6);
                    break;
            }
            

            
        }
    }
}
