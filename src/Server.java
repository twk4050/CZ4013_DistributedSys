import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;
import java.util.Random;
import java.net.DatagramPacket;
import java.util.HashMap;

public class Server {

    DatagramSocket socket;
    DatagramPacket packet;
    byte[] buffer;
    FlightOverview fOverview;
    SClientOverview clientOverview;

    HashMap<String, String> responseCache; // "59178" -> "Flight101 sg1 -> kl1"

    final String CASE_GET_FLIGHT = "1";
    final String CASE_GET_FLIGHT_BY_ID = "2";
    final String CASE_RESERVE_SEAT = "3";
    final String CASE_MONITOR_FLIGHT = "4";
    final String CASE_CANCEL_SEATS = "5";
    final String CASE_GET_FLIGHTS_BELOW_PRICE = "6";

    boolean AT_MOST_ONCE = true;
    boolean AT_LEAST_ONCE = true;

    final Integer RECEIVE_THRESHOLD = 5;
    final Integer SEND_THRESHOLD = 5;

    // constructor
    // specifiy specific Exception
    public Server(int portNo, FlightOverview fOverview, SClientOverview clientOverview) throws Exception {
        this.socket = new DatagramSocket(portNo);
        this.buffer = new byte[65535]; // change buffer size
        this.packet = new DatagramPacket(this.buffer, this.buffer.length);
        this.responseCache = new HashMap<>();

        this.fOverview = fOverview;
        this.clientOverview = clientOverview;
    }

    public void startListening() throws Exception {
        /*
         * At most once invocation semantics
         * Initialize to null
         * If AT_MOST_ONCE is true, initialize HashMap
         */

        System.out.println("At most once." + AT_MOST_ONCE);
        System.out.println("At least once." + AT_LEAST_ONCE);

        while (true) {
            Integer packetReceiveRate = getRandomNum(); // 0-10
            System.out.println("Success Rate for packet receive = " + packetReceiveRate + "0%");

            if (packetReceiveRate >= RECEIVE_THRESHOLD) { // packet is not lost
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

                String requestId = Marshalling.getRequestId(msg);
                boolean duplicated = false;

                // check whether responseCache has the requestId for AT_MOST_ONCE
                if (!responseCache.containsKey(requestId)) {
                    responseCache.put(requestId, ""); //
                    duplicated = false;
                } else {
                    duplicated = true;
                    System.out.println("Duplicated Message " + requestId + "|" + caseAndArgs + " received");

                    String duplicateMessage = responseCache.get(requestId);
                    this.sendPacket(client123, duplicateMessage);

                }

                if (duplicated == false) {
                    switch (caseId) {
                        case CASE_GET_FLIGHT:
                            String source = caseAndArgs[1];
                            String dest = caseAndArgs[2];

                            Response123 response1 = this.fOverview.getFlight(source, dest);
                            String response1Message = response1.getMessage();
                            responseCache.put(requestId, response1Message);

                            if (AT_LEAST_ONCE) {
                                atLeastOnceSendPacket(client123, response1Message);
                            } else {
                                this.sendPacket(client123, response1Message);
                            }

                            break;
                        case CASE_GET_FLIGHT_BY_ID:
                            int flightId = Integer.parseInt(caseAndArgs[1]);

                            Response123 response2 = this.fOverview.getFlightById(flightId);

                            String response2Message = response2.getMessage();
                            responseCache.put(requestId, response2Message);

                            if (AT_LEAST_ONCE) {
                                atLeastOnceSendPacket(client123, response2Message);
                            } else {
                                this.sendPacket(client123, response2Message);
                            }

                            break;
                        case CASE_RESERVE_SEAT:
                            int flightIdToReserve = Integer.parseInt(caseAndArgs[1]);
                            int numberOfSeats = Integer.parseInt(caseAndArgs[2]);
                            String username = caseAndArgs[3];
                            String password = caseAndArgs[4];

                            client123.setUsername(username); // ???
                            client123.setPassword(password); // ???
                            int userId = clientOverview.checkUserExistAndValidate(username, password);

                            String response3Message;
                            if (userId != -1) {
                                Response123 response3 = this.fOverview.reserveSeat(flightIdToReserve, numberOfSeats,
                                        client123);

                                // either flightId wrong or unable to reserve seats
                                if (response3.getStatus() == "-1") {
                                    response3Message = response3.getMessage();
                                    responseCache.put(requestId, response3Message);

                                    if (AT_LEAST_ONCE) {
                                        atLeastOnceSendPacket(client123, response3Message);
                                    } else {
                                        this.sendPacket(client123, response3Message);
                                    }
                                    this.refreshPacket();
                                    continue;
                                }

                                // if successful
                                response3Message = response3.getMessage();
                                responseCache.put(requestId, response3Message);

                                if (AT_LEAST_ONCE) {
                                    atLeastOnceSendPacket(client123, response3Message);
                                } else {
                                    this.sendPacket(client123, response3Message);
                                }

                                this.refreshPacket();

                                List<SClient> monitorList = response3.getMonitorList();
                                for (SClient c : monitorList) {
                                    long currentTimestamp = System.currentTimeMillis();

                                    if (c.getMonitorEndTime() < currentTimestamp) {
                                        if (AT_LEAST_ONCE) {
                                            atLeastOnceSendPacket(c, "Client removed from monitorList");
                                        } else {
                                            this.sendPacket(c, "Client removed from monitorList");
                                        }
                                        this.refreshPacket();
                                    } else {
                                        if (AT_LEAST_ONCE) {
                                            atLeastOnceSendPacket(c, response3Message);
                                        } else {
                                            this.sendPacket(c, response3Message);
                                        }
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
                            String response4Message = response4.getMessage();
                            responseCache.put(requestId, response4Message);

                            if (AT_LEAST_ONCE) {
                                atLeastOnceSendPacket(client123, response4Message);
                            } else {
                                this.sendPacket(client123, response4Message);
                            }

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

                                String response5Message = response5.getMessage();

                                if (AT_LEAST_ONCE) {
                                    atLeastOnceSendPacket(client123, response5Message);
                                } else {
                                    this.sendPacket(client123, response5Message);
                                }

                                this.refreshPacket();

                                List<SClient> monitorList = response5.getMonitorList();
                                for (SClient c : monitorList) {
                                    long currentTimestamp = System.currentTimeMillis();

                                    if (c.getMonitorEndTime() < currentTimestamp) {
                                        if (AT_LEAST_ONCE) {
                                            atLeastOnceSendPacket(c, "Client removed from monitorList");
                                        } else {
                                            this.sendPacket(c, "Client removed from monitorList");
                                        }
                                        this.refreshPacket();
                                    } else {
                                        if (AT_LEAST_ONCE) {
                                            atLeastOnceSendPacket(c, response5Message);
                                        } else {
                                            this.sendPacket(c, response5Message);
                                        }
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
                            String response6Message = response6.getMessage();
                            responseCache.put(requestId, response6Message);

                            if (AT_LEAST_ONCE) {
                                atLeastOnceSendPacket(client123, response6Message);
                            } else {
                                this.sendPacket(client123, response6Message);
                            }

                            break;
                        default:
                            System.out.println("in switch-case default statement: " + caseId);
                            this.sendPacket(client123, "echo Server: " + caseId);
                            break;
                    }
                    this.refreshPacket();

                }
            }

            if (packetReceiveRate < RECEIVE_THRESHOLD) {
                System.out.println("Simulating packet loss");
            }

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

    public static int getRandomNum() {
        Random rand = new Random();
        return rand.nextInt(10);
    }

    public void atLeastOnceSendPacket(SClient client, String responseMessage) throws Exception {
        Integer sendingRate = getRandomNum();
        System.out.println("At Least Once Rate = " + sendingRate + "0%");

        if (sendingRate > SEND_THRESHOLD) {
            System.out.println("Success message \n");
            this.sendPacket(client, responseMessage);
        } else { // Message unable to send
            System.out.println("Message failed to send\n");
        }
    }
}
