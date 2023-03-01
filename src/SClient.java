import java.net.InetAddress;

public class SClient {
    private int Id;
    private String username;
    private String password;

    private InetAddress address;
    private int portNo;

    private long monitorEndTime; //

    public SClient(InetAddress address, int portNo, int durationToMonitor) {
        this.address = address;
        this.portNo = portNo;

        long currentTimestamp = System.currentTimeMillis();
        this.monitorEndTime = currentTimestamp + durationToMonitor * 1000; // TODO: client inputs 100second, multiply
                                                                           // 1000 to make it milliseconds
    }

    public int getId() {
        return this.Id;
    }

    public InetAddress getAddress() {
        return this.address;
    }

    public int getPortNo() {
        return this.portNo;
    }

    public long getMonitorEndTime() {
        return this.monitorEndTime;
    }
}
