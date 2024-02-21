package sem1_chat;


public class ChatApp {


    public static void main(String[] args) {

        ServerGUI serv = new ServerGUI();
        ClientGUI client1 = new ClientGUI(serv);
        ClientGUI client2 = new ClientGUI(serv);
        ClientGUI client3 = new ClientGUI(serv);
    }
}
