package sem2_client_server.server;

public class User {
    private String host;
    private Integer port;
    private String login;
    private String password;
    private boolean connection;

    public String getLogin() {
        return login;
    }

    public void setConnection(boolean connected) {
        this.connection = connected;
    }

    public void sendToServer(String msg){

    }

    public void receiveFromServer(String msg){

    }
}
