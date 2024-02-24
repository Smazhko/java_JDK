package sem2_interfaces;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ClientGUI extends JFrame {
    // поля, необходимые для отрисовки окна
    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGHT = 300;
    private static final String WINDOW_TITLE = "Чат-клиент";
    private static final String LOGIN_BTN_TEXT = "Enter chat";
    private static final String SENDMSG_BTN_TEXT = "Send >>";

    // почти не задействованные параметры.
    // сравнение пользователей в логике программы происходит по login
    // генерация рандомного login
    private String serverIp = "127.0.0.1";
    private String serverSocket = "12345";
    private String login = "user" + new Random().nextInt(100,1000);
    private String password = "12345678";
    private DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    // статическое поле для более красивого расположения нескольких окон клиентов
    private static int counter;
    private int WINDOW_POSX = 150 + counter * (WINDOW_WIDTH + 50);

    private JTextArea chatConsole = new JTextArea();
    private JTextField serverIpTF = new JTextField(serverIp);
    private JTextField serverSocketTF = new JTextField(serverSocket);
    private JTextField loginTF = new JTextField(login);
    private JTextField passwordTF = new JTextField(password);
    private JButton loginBtn = new JButton(LOGIN_BTN_TEXT);
    private JTextField messageTF = new JTextField();
    private JButton sendMsgBtn = new JButton(SENDMSG_BTN_TEXT);

    //связь класса клиента с классом сервера
    private ServerGUI serv;

    private JPanel loginPnl = new JPanel(new GridLayout(2 , 3));

    public ClientGUI(ServerGUI serverGUI) {
        this.serv = serverGUI;
        counter ++;

        // отрисовка окна
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocation(WINDOW_POSX, 500);
        setTitle(WINDOW_TITLE);
        setResizable(false);

        // слушатель для JTextArea chatConsole - при движении каретки проверка доступности сервера
        // если сервер отключается - становится видимым логин-панель
        chatConsole.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                if (!serv.isServerWork()){
                    loginPnl.setVisible(true);
                }
            }
        });


        JPanel messagePnl = new JPanel(new BorderLayout()); // по умолчанию в панели сообщений Layout = FlowLayout. неизвестно почему
        JPanel spacePnl = new JPanel();

        // проверка для логина и пароля - если они пусты - устанавливается фокус на соответствующее поле
        // если НЕпусты оба - подключаемся к серверу
        loginBtn.addActionListener(e-> {
            if (loginTF.getText().isEmpty()) {
                loginTF.requestFocus(true);
            } else if (passwordTF.getText().isEmpty()) {
                passwordTF.requestFocus(true);
            } else {
                connectToServer();
                messageTF.requestFocus(true);
            }
        });

        // отправка сообщения по КНОПКЕ
        sendMsgBtn.addActionListener(e -> {
            if (isMsgValid()) sendMsg();
        });

        // отправка сообщения по нажатию ENTER в поле ввода
        // Выполняем действие, когда пользователь нажимает Enter
        messageTF.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && isMsgValid()) {
                    sendMsg();
                }
            }
        });

        // наполнение логин-панели
        loginPnl.add(serverIpTF);
        loginPnl.add(serverSocketTF);
        loginPnl.add(spacePnl);
        loginPnl.add(loginTF);
        loginPnl.add(passwordTF);
        loginPnl.add(loginBtn);
        add(loginPnl, BorderLayout.NORTH);

        // оформление консоли
        chatConsole.setEditable(false);
        //включаем перенос по словам
        chatConsole.setLineWrap(true);
        chatConsole.setWrapStyleWord(true);
        // Создаем JScrollPane и добавляем в него JTextArea
        JScrollPane scrollPane = new JScrollPane(chatConsole);
        // Устанавливаем полосу прокрутки только по вертикали
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        // Добавляем JScrollPane на панель содержимого окна
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // наполнение панели отправки сообщений
        messagePnl.add(messageTF, BorderLayout.CENTER);
        messagePnl.add(sendMsgBtn, BorderLayout.EAST);
        add(messagePnl, BorderLayout.SOUTH);

        setVisible(true);

        // слушатель ОКНА со списком команд при ЗАКРЫТИИ окна
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                disconnectFromServer();
                setVisible(false); // всё работает и без ЭТОЙ строки, но на всякий пожарный
            }
        });
    }


    // действия при отключении клиента (закрытие окна)
    private boolean disconnectFromServer() {
        serv.removeUser(this);
        return true;
    }


    // действия при ПОДКЛЮЧЕНИИ к серверу
    // получение содержимого полей в момент нажатия на кнопку
    // передаём СЕБЯ в сервер, чтоб он добавил в список
    // при успешном логе - делаем невидимой лог-панель
    // если связи с сервером нет - сообщение
    public boolean connectToServer(){
        login = loginTF.getText();
        password = passwordTF.getText();
        if (serv.isServerWork()){
            serv.addUser(this);
            loginPnl.setVisible(false);
            return true;
        } else {
            consolePrintMsg("Невозможно установить соединение с сервером. Попробуйте ещё раз.");
        }
        return false;
    }


    // вывод в JTextArea chatConsole ЛЮБОГО сообщения
    public void consolePrintMsg(String sysMsgText){
        chatConsole.append("(" + timeFormat.format(new Date()) + ") " + sysMsgText + "\n");
    }


    // проверка сообщения на валидность - пустое или слишком длинное
    private boolean isMsgValid(){
        String message = messageTF.getText();
        return  (!message.isEmpty() && message.length() < 255);
    }


    // отправка сообщения на сервер, опустошение поля ввода
    public void sendMsg(){
        serv.getUserMessage(this, messageTF.getText());
        messageTF.setText("");
    }

    // геттер на логин (для идентификации)
    public String getLogin() {
        return login;
    }
}
