import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.DatagramPacket;

public class Server {

    DatagramSocket socket;
    byte[] buffer;
    DatagramPacket packet;

    // constructor
    // specifiy specific Exception
    public Server(int portNo) throws Exception {
        this.socket = new DatagramSocket(portNo);
        this.buffer = new byte[65535]; // change buffer size
        this.packet = new DatagramPacket(buffer, buffer.length);
    }

    public void startListening() throws Exception {
        System.out.println("Server running at port " + socket.getLocalPort());

        while (true) {
            // waiting to receive packets
            this.socket.receive(packet); // blocking
            String messageFromClient = convertByteToStringBuilder(this.buffer).toString();
            System.out.println("From Client: " + messageFromClient);

            // getting client's info
            int clientPort = packet.getPort();
            InetAddress clientAddress = packet.getAddress();
            System.out.println("client address/port:" + clientAddress + "/" + clientPort);

            // crafting response back to client
            String messageToClient = "Server: " + messageFromClient;
            byte[] rBuffer = messageToClient.getBytes();

            DatagramPacket response = new DatagramPacket(rBuffer, rBuffer.length, clientAddress, clientPort);
            socket.send(response);

            // refresh at the end
            refreshBufferAndPacket();
        }
    }

    public void refreshBufferAndPacket() {
        this.buffer = new byte[65535];
        this.packet = new DatagramPacket(buffer, buffer.length);
    }

    // A utility method to convert the byte array
    // data into a string representation.
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
