package sem2_client_server_my.server;
// ************************************************
// ПУСТОЙ КЛАСС - чтобы приложение запустилось
// ************************************************

public class MyConnector implements AnyConnector{
    @Override
    public void sendToUser(Object address, String message) {

    }

    @Override
    public void sendToAll(String message) {

    }

    @Override
    public SocketMsg recieveFromUser(String rawMessage) {
        return null;
    }

    @Override
    public boolean start() {
        return false;
    }

    @Override
    public boolean stop() {
        return false;
    }
}
