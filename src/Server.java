import java.net.DatagramSocket;
import java.net.InetAddress;
import java.io.IOException;
import java.net.DatagramPacket;

public class Server {

    DatagramSocket socket;
    DatagramPacket packet;
    byte[] buffer;
    FlightOverview fOverview;

    final String CASE_GET_FLIGHT = "1";
    final String CASE_GET_FLIGHT_BY_ID = "2";
    final String CASE_RESERVE_SEAT = "3";
    final String CASE_MONITOR_FLIGHT = "4";
    final String CASE_CANCEL_SEATS = "5";
    final String CASE_GET_FLIGHTS_BELOW_PRICE = "6";

    // constructor
    // specifiy specific Exception
    public Server(int portNo, FlightOverview fOverview) throws Exception {
        this.socket = new DatagramSocket(portNo);
        this.buffer = new byte[65535]; // change buffer size
        this.packet = new DatagramPacket(this.buffer, this.buffer.length);

        this.fOverview = fOverview;
    }

    public void startListening() throws Exception {
        System.out.println("Server running at port " + socket.getLocalPort());

        while (true) {
            // waiting to receive packets
            this.socket.receive(packet); // blocking
            String messageFromClient = Marshalling.convertByteToStringBuilder(this.buffer).toString();

            // getting client's info
            int clientPort = packet.getPort();
            InetAddress clientAddress = packet.getAddress();
            System.out.println("From Client" + clientAddress + "/" + clientPort + ": " + messageFromClient);

            // crafting response back to client
            String messageToClient = messageFromClient; // simple echo server for now
            byte[] rBuffer = messageToClient.getBytes();

            DatagramPacket response = new DatagramPacket(rBuffer, rBuffer.length, clientAddress, clientPort);
            socket.send(response);

            // refresh at the end
            refreshPacket();
        }
    }

    public void testStartListening() throws Exception {
        while (true) {
            this.socket.receive(this.packet); // blocking

            SClient client = new SClient(packet.getAddress(), packet.getPort());

            String msg = Marshalling.convertByteToStringBuilder(this.buffer).toString();

            /*
             * some processing here <requestId:requirement1|flightId>
             * // 59178:1|arg1|arg2|
             * // 1. process requirementId first to enter case statement
             * // 2. process again in switch-case to get fn arguments
             */
            String[] caseAndArgs = Marshalling.getCaseAndFnArgs(msg);
            String caseId = caseAndArgs[0];

            System.out.println(
                    "caseid received: " + caseId + " from: " + client.getAddress() + "/"
                            + client.getPortNo());

            switch (caseId) {
                case CASE_GET_FLIGHT:
                    break;
                case CASE_GET_FLIGHT_BY_ID:
                    int flightId = Integer.parseInt(caseAndArgs[1]);

                    Flight f = this.fOverview.getFlightById(flightId);
                    this.sendPacket(client, f.toString());
                    // System.out.println(f);
                    break;
                case CASE_RESERVE_SEAT:
                    break;
                case CASE_MONITOR_FLIGHT:
                    break;
                case CASE_CANCEL_SEATS:
                    break;
                case CASE_GET_FLIGHTS_BELOW_PRICE:
                    break;
                default:
                    System.out.println("in switch-case default statement: " + caseId);
                    this.sendPacket(client, "echo Server: " + caseId);
                    break;
            }
            this.refreshPacket();
        }
    }

    public void sendPacket(SClient client, String messageString) throws Exception {
        byte[] buffer = messageString.getBytes();
        InetAddress ip = client.getAddress();
        int clientPortNum = client.getPortNo();

        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, ip, clientPortNum);

        this.socket.send(packet);
    }

    public void refreshPacket() {
        this.buffer = new byte[65535];
        this.packet = new DatagramPacket(this.buffer, this.buffer.length);
    }
}
