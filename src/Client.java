import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {

    DatagramSocket socket;
    int serverPortNum;

    public Client(int serverPortNum) throws IOException {
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

    public static void main(String args[]) throws IOException, Exception {

        Client c1 = new Client(1234);
        Scanner sc = new Scanner(System.in);

        while (true) {
            String input = sc.nextLine();
            c1.sendPacket(input);

            c1.receivePacket();
        }
    }

    // TODO: shift this fn into Utilities.java
    // A utility method to convert the byte array data into a string representation.
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
}