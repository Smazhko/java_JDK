package sem2_client_server_my.server;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

// МЕХАНИЗМ OBSERVER-LISTENER: ПРЕЗЕНТЕР реализует метод propertyChange интерфейса PropertyChangeListener
// теперь объект класса ПРЕЗЕНТЕР можно обрабатывать как тип PropertyChangeListener, что мы и делаем во VIEW
public class ServerPresenter implements PropertyChangeListener {
    ServerModel server;
    ServerUserInterface view;
    AnyConnector connector = new MyConnector(); // некое абстрактное НЕЧТО, через которое можно получать информацию от пользователей


    // конструктор презентера
    public ServerPresenter(ServerModel server, ServerUserInterface view) {
        this.server = server;
        this.view = view;

        // МЕХАНИЗМ OBSERVER-LISTENER: передаём себя во VIEW, чтобы он знал, кто его слушает
        // view.addPropertyChangeListener(this);
    }

    @Override
    // МЕХАНИЗМ OBSERVER-LISTENER: получаем из VIEW данные о нажатых кнопках или закрытом окне
    // запускаем эти данные в соответствующие обработчики
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()){
            case "serverWork":
                checkServerWork((boolean) evt.getNewValue());
                break;
            case "uiWork":
                checkUiWork((boolean) evt.getNewValue());
                break;
            default:

        }
    }


    private void checkServerWork(boolean isServerWork) {
        if (!isServerWork)
            serverStop();
        else
            serverStart();
    }


    private void checkUiWork(boolean isUiWork) {
        if (!isUiWork){
            connector.stop();
            System.exit(0);
        }
    }


    private void serverStart(){
        view.printToConsole("Server started successfully.");
        view.printToConsole("Waiting for clients...");
        server.start();
        connector.start();
    }


    private void serverStop(){
        // прекратить слушать сокеты
        // остановить сервер
        // отправить сообщение всем пользователям об остановке
        // отправить сообщение на свой UI
        server.stop();
        view.printToConsole("Server stopped.");
        connector.sendToAll("Сервер завершил свою работу");
    }






    public boolean addUser(User currentUser){
        if (server.addUser(currentUser)){
            view.printToConsole("User " + currentUser.getLogin() + " connected to server.");
            connector.sendToUser(currentUser, "Соединение установлено. Добро пожаловать, " + currentUser.getLogin() + "!");
            connector.sendToAll("Пользователь " + currentUser.getLogin() + " вошёл в чат.");
            connector.sendToUser(currentUser, server.getHistory());
            return true;
        }
        else
            return false;
    }


    public void removeUser(User userToRemove) {
        if (server.removeUser(userToRemove)) {
            view.printToConsole("User " + userToRemove.getLogin() + " disconnected.");
            connector.sendToAll("Пользователь " + userToRemove.getLogin() + " покинул чат.");
        } else {
            view.printToConsole("Such login (" + userToRemove.getLogin() + ") is absent in user list.");
        }
    }


    public void receiveMessage(User user, String msgText){
        view.printToConsole(user.getLogin() + ": " + msgText);
        server.receiveMessage(user, msgText);
        connector.sendToAll(user.getLogin() + ": " + msgText);
    }


    // этот метод, вероятно, необходимо обернуть в отдельный поток с бесконечным циклом,
    // чтобы постоянно слушать CONNECTOR, в котором в перспективе будет находиться СОКЕТ
    private void listenConnector(SocketMsg msg){
        switch (msg.getType()){
            case TEXT -> receiveMessage(msg.getUser(), msg.getMsgText());
            case LOGIN -> addUser(msg.getUser());       // надо бы проверять ещё и пароль, но это уже другая история
            case LOGOUT -> removeUser(msg.getUser());
        }
    }



    // ИДЕЯ НАБЛЮДЕНИЯ ЗА ОКНОМ БЛАГОДАРЯ ПОТОКУ с бесконечным циклом - ВАРИАНТ обработки действий, произведенных в окне
    // наблюдатель за view - подразумевает то, что VIEW про PRESENTER совсем ничего не знает
    // во VIEW имеются условленные интерфейсом методы
    // и PRESENTER с их помощью наблюдает за изменениями переменных во VIEW
    // не пустил в ход из-за того, что бесконечный цикл, вероятно, будет сильно нагружать процессор
    // к тому же в проекте планируется запуск THREAD-ов с сокетами, и, НАВЕРНОЕ, ещё один поток с циклом будет лишним
    /*
    private Runnable viewListener = () -> {
        boolean previousServerWorkState = view.isServerWork();
        boolean continueFlag = true;
        while(continueFlag){
            boolean currentServerWorkState = view.isServerWork();
            if (previousServerWorkState != currentServerWorkState){
                if (currentServerWorkState)
                    System.out.println("Сервер ВКЛ");
                else
                    System.out.println("Сервер ВЫКЛ");
                previousServerWorkState = view.isServerWork();
            }
            if (!view.isUiWork()){
                continueFlag = false;
                // stop();
                System.out.println("окно закрыто");
            }
            // вставляем сон, чтобы не нагружать процессор
            try {
                Thread.sleep(80);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    // пуск потока при запуске презентера
    // логика потока вынесена в поле класса (private Runnable viewListener - выше)
    public void start(){
        Thread listenViewThr = new Thread(viewListener);
        listenViewThr.start();
        try {
            listenViewThr.join();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        listenViewThr.interrupt();
        System.exit(0);
    }
     */
}
