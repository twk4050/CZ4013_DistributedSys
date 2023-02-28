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

    public void receivePacket() throws IOException {
        byte[] buffer = new byte[65535];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        this.socket.receive(packet); // blocking

        InetAddress serverAddress = packet.getAddress();
        int serverPort = packet.getPort();

        String messageFromClient = convertByteToStringBuilder(buffer).toString();
        System.out.println("From Server" + serverAddress + "/" + serverPort + ": " + messageFromClient);
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
        "1. Check Flight ID\n" +
        "2. Check Flight Information\n" +
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
            
            switch(userinput){
                case 1:
                    m = e.queryFlightId();
                    msg = m.messageToString();
                    c1.sendPacket(msg);
                    c1.receivePacket();
                    break;
                case 2:
                    m = e.queryFlightInfo();
                    msg = m.messageToString();
                    c1.sendPacket(msg);
                    c1.receivePacket();
                    break;
                case 3:
                    m = e.reserveSeats();
                    msg = m.messageToString();
                    c1.sendPacket(msg);
                    c1.receivePacket();
                    break;
                case 4:
                    m = e.monitorflight();
                    msg = m.messageToString();
                    c1.sendPacket(msg);
                    c1.receivePacket();
                    break;
                case 5:
                    m = e.cancelSeat();
                    msg = m.messageToString();
                    c1.sendPacket(msg);
                    c1.receivePacket();
                    break;
                case 6:
                    m = e.getFlightBelowCertainPrice();
                    msg = m.messageToString();
                    c1.sendPacket(msg);
                    c1.receivePacket();
                    break;
            }
            

            
        }
    }
}
