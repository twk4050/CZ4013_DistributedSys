import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;
import java.io.IOException;
import java.net.DatagramPacket;

public class Server {

    DatagramSocket socket;
    DatagramPacket packet;
    byte[] buffer;
    FlightOverview fOverview;
    SClientOverview clientOverview;

    final String CASE_GET_FLIGHT = "1";
    final String CASE_GET_FLIGHT_BY_ID = "2";
    final String CASE_RESERVE_SEAT = "3";
    final String CASE_MONITOR_FLIGHT = "4";
    final String CASE_CANCEL_SEATS = "5";
    final String CASE_GET_FLIGHTS_BELOW_PRICE = "6";

    // constructor
    // specifiy specific Exception
    public Server(int portNo, FlightOverview fOverview, SClientOverview clientOverview) throws Exception {
        this.socket = new DatagramSocket(portNo);
        this.buffer = new byte[65535]; // change buffer size
        this.packet = new DatagramPacket(this.buffer, this.buffer.length);

        this.fOverview = fOverview;
        this.clientOverview = clientOverview;
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
                    // System.out.println("\nPlease enter the source of the flight: ");
                    // source = readuserinput.inputStr();
                    // System.out.println("\nPlease enter the destination of the flight: ");
                    // destination = readuserinput.inputStr();
                    // message msg = client.queryFlightId(source, destination);
                    // return msg;
                    String source = caseAndArgs[1];
                    String dest = caseAndArgs[2];
                    Response123 response1 = this.fOverview.getFlight(source, dest);

                    this.sendPacket(client, response1.getStatus());

                    break;
                case CASE_GET_FLIGHT_BY_ID:
                    int flightId = Integer.parseInt(caseAndArgs[1]);

                    Flight f = this.fOverview.getFlightById(flightId);
                    this.sendPacket(client, f.toString());
                    // System.out.println(f);
                    break;
                case CASE_RESERVE_SEAT:
                    int flightIdToReserve = Integer.parseInt(caseAndArgs[1]);
                    int numberOfSeats = Integer.parseInt(caseAndArgs[2]);
                    String username = caseAndArgs[3];
                    String password = caseAndArgs[4];

                    client.setUsername(username); // ???
                    client.setPassword(password); // ???
                    int userId = clientOverview.checkUserExistAndValidate(username, password);
                    if (userId != -1) {
                        Response123 response3 = this.fOverview.reserveSeat(flightIdToReserve, numberOfSeats, client);

                        List<Integer> seatsNum = response3.getSeatsReserved();
                        String returnMsg = response3.getStatus();
                        for (int i : seatsNum) {
                            returnMsg += Integer.toString(i) + "/";
                        }

                        this.sendPacket(client, returnMsg);
                    } else {
                        // send error message
                        this.sendPacket(client, "user does not exist");
                    }
                    break;
                case CASE_MONITOR_FLIGHT:
                    break;
                case CASE_CANCEL_SEATS:
                    // System.out.println("\nPlease enter the flight ID: ");
                    // flightId = readuserinput.inputInt();
                    // System.out.println("\nPlease enter Username: ");
                    // userName = readuserinput.inputStr();
                    // System.out.println("\nPlease enter Password: ");
                    // passWord = readuserinput.inputPass();
                    // message msg = client.cancelSeat(flightId, userName, passWord);
                    // return msg;
                    int flightIdToCancel = Integer.parseInt(caseAndArgs[1]);
                    String username5 = caseAndArgs[2];
                    String password5 = caseAndArgs[3];

                    client.setUsername(username5); // ???
                    client.setPassword(password5); // ???
                    int userId5 = clientOverview.checkUserExistAndValidate(username5, password5);
                    if (userId5 != -1) {

                        Response123 response5 = this.fOverview.cancelSeat(flightIdToCancel, client);

                        this.sendPacket(client, response5.getStatus());
                    } else {
                        // send error message
                        this.sendPacket(client, "user does not exist");
                    }

                    break;
                case CASE_GET_FLIGHTS_BELOW_PRICE:
                    Float priceThreshold = Float.parseFloat(caseAndArgs[1]);
                    Response123 response6 = this.fOverview.getFlightBelowCertainPrice(priceThreshold);

                    this.sendPacket(client, response6.getStatus());
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
