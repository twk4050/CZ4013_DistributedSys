import java.net.InetAddress;

public class SClient {
    private int Id;
    private String username;
    private String password;

    private InetAddress address;
    private int portNo;

    private long monitorEndTime; //

    public SClient(InetAddress address, int portNo) {
        this.address = address;
        this.portNo = portNo;
    }

    public SClient(InetAddress address, int portNo, int durationToMonitor) {
        this.address = address;
        this.portNo = portNo;

        long currentTimestamp = System.currentTimeMillis();
        this.monitorEndTime = currentTimestamp + durationToMonitor * 1000; // TODO: client inputs 100second, multiply //
                                                                           // 1000 to make it milliseconds
    }

    public SClient(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return this.Id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public InetAddress getAddress() {
        return this.address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public int getPortNo() {
        return this.portNo;
    }

    public void setPortNo(int portNo) {
        this.portNo = portNo;
    }

    public long getMonitorEndTime() {
        return this.monitorEndTime;
    }

    public void setMonitorEndTime(long monitorEndTime) {
        this.monitorEndTime = monitorEndTime;
    }

    public boolean validateUser(String usernameInput, String passwordInput) {
        if (this.username.equals(usernameInput) && this.password.equals(passwordInput)) {
            return true;
        }
        return false;
    }
}
