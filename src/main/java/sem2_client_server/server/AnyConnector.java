package sem2_client_server.server;

public interface AnyConnector {
    void sendToUser(Object address, String message);

    void sendToAll(String message);

    SocketMsg recieveFromUser(String rawMessage);

    boolean start();

    boolean stop();

}
