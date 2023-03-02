import java.util.List;

public class SClientOverview {

    List<SClient> clients;

    public SClientOverview(List<SClient> clients) {
        this.clients = clients;
    }

    // TODO: split function into user exist then validate
    public int checkUserExistAndValidate(String usernameInput, String passwordInput) {
        for (SClient client : this.clients) {
            int userId = client.validateUser(usernameInput, passwordInput);
            if (userId != -1) {
                return userId;
            }
        }
        return -1;
    }
}
