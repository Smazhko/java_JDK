package sem2_client_server.server;

public class ServerApp {
    public static void main(String[] args) {

        // МОДЕЛЬ ничего не знает про ПРЕЗЕНТЕР
        ServerModel chatServer = new ServerModel();

        ServerUserInterface view = new ServerGUI();

        ServerPresenter sp = new ServerPresenter(chatServer, view);

        // ПРЕДСТАВЛЕНИЕ VIEW слабо связано через абстракцию с ПРЕЗЕНТЕРом
        view.addPropertyChangeListener(sp);
    }
}
