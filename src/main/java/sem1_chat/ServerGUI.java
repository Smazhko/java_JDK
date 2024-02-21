package sem1_chat;


import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;


public class ServerGUI extends JFrame {
    // поля, необходимые для отрисовки окна
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int WINDOW_POSX = (screenSize.width - WINDOW_WIDTH) / 2;
    private int WINDOW_POSY = 200;
    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGHT = 300;
    private static final String START_BTN_TEXT = "ПУСК";
    private static final String STOP_BTN_TEXT = "СТОП";
    private static final String WINDOW_TITLE = "Чат-сервер";

    // переменные для бизнес логики сервера
    private boolean serverWork;
    private File historyFile = new File("src/main/java/sem1_chat/log.txt");
    private List<String> msgHistory = new ArrayList<>();
    private List<ClientGUI> userList = new ArrayList<>();

    private DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    private DateFormat dateFormat1 = new SimpleDateFormat("dd.MM.yyyy");

    // элементы окна
    JButton startBtn = new JButton(START_BTN_TEXT);
    JButton stopBtn = new JButton(STOP_BTN_TEXT);
    JTextArea logConsole = new JTextArea();

    // конструктор окна сервера
    public ServerGUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocation(WINDOW_POSX, WINDOW_POSY);
        setTitle(WINDOW_TITLE);
        setResizable(false);

        JPanel buttonsPnl = new JPanel();

        logConsole.setEditable(false);
        //включаем перенос по словам
        logConsole.setLineWrap(true);
        logConsole.setWrapStyleWord(true);

        // Создаем JScrollPane и добавляем в него JTextArea
        JScrollPane scrollPane = new JScrollPane(logConsole);

        // Устанавливаем полосу прокрутки только по вертикали
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // обработка кнопки ПУСК сервера
        startBtn.addActionListener(e -> {
            if (!isServerWork()){
                serverWork = true;
                consolePrint("Server started successfully.");
                consolePrint("Waiting for clients...");
            }
            else {
                consolePrint("Server already run.");
            }
        });

        // обработка СТОП сервера, в т.ч. очистка списка пользователей и сохранение истории
        stopBtn.addActionListener(e -> {
            if (isServerWork()){
                serverWork = false;
                consolePrint("Server stopped.");
                sendToAll("Сервер завершил свою работу");
                userList.clear();
                saveChatHistory();
            }
        });

        buttonsPnl.setLayout(new GridLayout(1, 2));
        buttonsPnl.add(startBtn);
        buttonsPnl.add(stopBtn);
        add(buttonsPnl, BorderLayout.SOUTH);
        // Добавляем JScrollPane на панель содержимого окна
        getContentPane().add(scrollPane, BorderLayout.CENTER);


        setVisible(true);
    }


    // геттер serverWork
    public boolean isServerWork() {
        return serverWork;
    }


    // сохранение истории чата
    // если файла нет - он создаётся
    // если файл не занят, то запись в файл построчно из списка msgHistory
    private void saveChatHistory(){
        if (!historyFile.exists()){
            try {
                historyFile.createNewFile();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

        if (historyFile.canWrite()){
            try (FileWriter fw = new FileWriter(historyFile, false)){
                for(String item: msgHistory){
                    fw.write(item + "\n");
                }
            }
            catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
    }


    // загрузка истории из файла
    // если файла нет - он создаётся
    // если файл доступень для чтения - загрузка из файла в список msgHistory
    private List<String> loadChatHistory (){
        if (!historyFile.exists()){
            try {
                historyFile.createNewFile();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

        if (historyFile.canRead()){
            try (Scanner scan = new Scanner(historyFile)){
                msgHistory.clear();
                while (scan.hasNext()){
                    msgHistory.add(scan.nextLine());
                }
                return msgHistory;
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return null;
    }


    // вывод ЛЮБОГО сообщения в JTextArea logConsole с временной отметкой
    private void consolePrint(String msg){
        logConsole.append(dateFormat.format(new Date()) + ": " + msg + "\n");
    }


    // добавление пользователя в список
    // вызывается из клиента - клиент просит добавить себя в список
    // это происходит при очередном коннекте пользователя к серверу
    // попутно - загружается история сообщений и отправляется только одному подключившемуся пользователю
    public boolean addUser(ClientGUI user){
        userList.add(user);
        consolePrint("User " + user.getLogin() + " connected to server.");
        sendToCurrentUser(user, "Соединение установлено. Добро пожаловать, " + user.getLogin() + "!");
        sendToCurrentUser(user, "Сегодня " + dateFormat1.format(new Date()) + " г.");
        sendToAll("Пользователь " + user.getLogin() + " вошёл в чат.");
        String history = loadChatHistory().toString().replaceAll("\\[\\]","");
        history = history.replaceAll("[\\[\\]]","");
        history = history.replaceAll(", ", "\n");
        user.consolePrintMsg(
                "История сообщений ============\n"
                + history +
                " =====================================");
        return true;
    }


    // удаление пользователя из списка
    // инициируется клиентом при отключении
    // происходит отправка сообщения ВСЕМ присутствующим о том, что пользователь отключился
    public void removeUser(ClientGUI user) {
        if (userList.contains(user)) {
            userList.remove(user);
            consolePrint("User " + user.getLogin() + " disconnected.");
            sendToAll("Пользователь " + user.getLogin() + " покинул чат.");
        }
        else {
            consolePrint("Such login (" + user.getLogin() + ") is absent in user list.");
        }
    }


    // отправка сообщения ВСЕМ клиентам, которые присутствуют в списке
    private void sendToAll(String msg){
        if (!userList.isEmpty()) {
            for (ClientGUI user : userList) {
                user.consolePrintMsg(msg);
            }
        }
    }


    // отправка сообщения только ОДНОМУ клиенту
    private void sendToCurrentUser(ClientGUI user, String msg){
         user.consolePrintMsg(msg);
    }


    // получение сервером сообщения от пользователя и отправка его ВСЕМ, кто в списке
    // сохранение сообщения в истории
    public void getUserMessage(ClientGUI user, String msgText) {
        consolePrint(user.getLogin() + ": " + msgText);
        sendToAll(user.getLogin() + ": " + msgText);
        msgHistory.add("(" + dateFormat.format(new Date()) + ") " + user.getLogin() + ": " + msgText);
        saveChatHistory();
    }
}
