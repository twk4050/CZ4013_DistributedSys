import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;
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
        while (true) {
            this.socket.receive(this.packet); // blocking

            InetAddress clientAddress = packet.getAddress();
            int clientPortNo = packet.getPort();
            SClient client123 = new SClient(clientAddress, clientPortNo);

            String msg = Marshalling.convertByteToStringBuilder(this.buffer).toString();

            /*
             * some processing here <requestId:requirementId|flightId>
             * // 59178:1|arg1|arg2|
             * // 1. process requirementId first to enter case statement
             * // 2. process again in switch-case to get fn arguments
             */

            String[] caseAndArgs = Marshalling.getCaseAndFnArgs(msg);
            String caseId = caseAndArgs[0];

            System.out.println("caseid: " + caseId + " from: " + clientAddress + "/" + clientPortNo);

            switch (caseId) {
                case CASE_GET_FLIGHT:
                    String source = caseAndArgs[1];
                    String dest = caseAndArgs[2];

                    Response123 response1 = this.fOverview.getFlight(source, dest);

                    this.sendPacket(client123, response1.getMessage());

                    break;
                case CASE_GET_FLIGHT_BY_ID:
                    int flightId = Integer.parseInt(caseAndArgs[1]);

                    Response123 response2 = this.fOverview.getFlightById(flightId);
                    this.sendPacket(client123, response2.getMessage());

                    break;
                case CASE_RESERVE_SEAT:
                    int flightIdToReserve = Integer.parseInt(caseAndArgs[1]);
                    int numberOfSeats = Integer.parseInt(caseAndArgs[2]);
                    String username = caseAndArgs[3];
                    String password = caseAndArgs[4];

                    client123.setUsername(username); // ???
                    client123.setPassword(password); // ???
                    int userId = clientOverview.checkUserExistAndValidate(username, password);
                    if (userId != -1) {
                        Response123 response3 = this.fOverview.reserveSeat(flightIdToReserve, numberOfSeats, client123);

                        // either flightId wrong or unable to reserve seats
                        if (response3.getStatus() == "-1") {
                            this.sendPacket(client123, response3.getMessage());
                            this.refreshPacket();
                            continue;
                        }

                        // if successful
                        String returnMsg = response3.getMessage();

                        this.sendPacket(client123, returnMsg);
                        this.refreshPacket();

                        List<SClient> monitorList = response3.getMonitorList();
                        for (SClient c : monitorList) {
                            long currentTimestamp = System.currentTimeMillis();

                            if (c.getMonitorEndTime() < currentTimestamp) {
                                this.sendPacket(c, "Client removed from monitorList");
                                this.refreshPacket();
                            } else {
                                this.sendPacket(c, returnMsg);
                                this.refreshPacket();
                            }
                        }
                        response3.getFlight().removeExpiredFromMonitorList();

                    } else {
                        // send error message
                        this.sendPacket(client123, "user does not exist");
                    }
                    break;
                case CASE_MONITOR_FLIGHT:
                    int flightId4 = Integer.parseInt(caseAndArgs[1]);
                    long duration = Long.parseLong(caseAndArgs[2]); // getting input in seconds

                    client123.setMonitorEndTimeInMs(duration);

                    Response123 response4 = this.fOverview.monitorFlight(flightId4, client123);
                    this.sendPacket(client123, response4.getMessage());

                    break;
                case CASE_CANCEL_SEATS:
                    int flightIdToCancel = Integer.parseInt(caseAndArgs[1]);
                    String username5 = caseAndArgs[2];
                    String password5 = caseAndArgs[3];

                    client123.setUsername(username5); // ???
                    client123.setPassword(password5); // ???
                    int userId5 = clientOverview.checkUserExistAndValidate(username5, password5);
                    if (userId5 != -1) {

                        Response123 response5 = this.fOverview.cancelSeat(flightIdToCancel, client123);

                        String returnMsg = response5.getMessage();

                        this.sendPacket(client123, returnMsg);
                        this.refreshPacket();

                        List<SClient> monitorList = response5.getMonitorList();
                        for (SClient c : monitorList) {
                            long currentTimestamp = System.currentTimeMillis();

                            if (c.getMonitorEndTime() < currentTimestamp) {
                                this.sendPacket(c, "Client removed from monitorList");
                                this.refreshPacket();
                            } else {
                                this.sendPacket(c, returnMsg);
                                this.refreshPacket();
                            }
                        }
                        response5.getFlight().removeExpiredFromMonitorList();
                    } else {
                        // send error message
                        this.sendPacket(client123, "user does not exist");
                    }

                    break;
                case CASE_GET_FLIGHTS_BELOW_PRICE:
                    Float priceThreshold = Float.parseFloat(caseAndArgs[1]);

                    Response123 response6 = this.fOverview.getFlightBelowCertainPrice(priceThreshold);

                    this.sendPacket(client123, response6.getMessage());

                    break;
                default:
                    System.out.println("in switch-case default statement: " + caseId);
                    this.sendPacket(client123, "echo Server: " + caseId);
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
