package sem2_client_server.server;


import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


// как вариант - использовать Шаблон Наблюдатель (Observer)
// Добавить в класс ПОЛЕ ДЛЯ РАЗДАВАТЕЛЯ НОВОСТЕЙ
// в конструкторе - создать его объект и добавить класс сервер к слушателям
// https://javarush.com/groups/posts/3421-shablon-nabljudateljh-observer
// однако, при этом view GUI должен знать про слушателя - презентер, чего не хотелось бы

public class ServerGUI extends JFrame implements ServerUserInterface  {
    // поля, необходимые для отрисовки окна
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int WINDOW_POSX = (screenSize.width - WINDOW_WIDTH) / 2;
    private int WINDOW_POSY = 200;
    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGHT = 300;
    private static final String START_BTN_TEXT = "ПУСК";
    private static final String STOP_BTN_TEXT = "СТОП";
    private static final String WINDOW_TITLE = "Чат-сервер";


    private boolean serverWork; // флаг - работает ли сервер
    private boolean uiWork;     // флаг - работает ли окно


    // МЕХАНИЗМ OBSERVER-LISTENER:
    // используя support мы можем добавлять или удалять слушателей изменения переменных в GUI
    // благодаря этому механизму мы ослабляем связь между VIEW и PRESENTER
    private PropertyChangeSupport support;


    // элементы окна
    JButton startBtn = new JButton(START_BTN_TEXT);
    JButton stopBtn = new JButton(STOP_BTN_TEXT);
    JTextArea logConsole = new JTextArea();


    // конструктор окна сервера
    public ServerGUI() {
        // МЕХАНИЗМ OBSERVER-LISTENER:
        // инициализирует поддержку прослушивания изменений свойств для данного экземпляра класса ServerGUI
        support = new PropertyChangeSupport(this);

        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocation(WINDOW_POSX, WINDOW_POSY);
        setTitle(WINDOW_TITLE);
        setResizable(false);

        JPanel buttonsPnl = new JPanel();

        logConsole.setEditable(false);
        // ↓ включаем перенос по словам
        logConsole.setLineWrap(true);
        logConsole.setWrapStyleWord(true);

        // ↓ Создаем JScrollPane и добавляем в него JTextArea logConsole
        JScrollPane scrollPane = new JScrollPane(logConsole);

        // ↓ Устанавливаем полосу прокрутки только по вертикали
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Действия для кнопок ===========================
        // Обработка кнопки ПУСК сервера
        startBtn.addActionListener(e -> {
            setServerWork(true);
            startBtn.setEnabled(false);
            stopBtn.setEnabled(true);
        });
        // Обработка СТОП сервера, в т.ч. очистка списка пользователей и сохранение истории
        stopBtn.addActionListener(e -> {
            setServerWork(false);
            startBtn.setEnabled(true);
            stopBtn.setEnabled(false);
        });
        stopBtn.setEnabled(false);

        buttonsPnl.setLayout(new GridLayout(1, 2));
        buttonsPnl.add(startBtn);
        buttonsPnl.add(stopBtn);
        add(buttonsPnl, BorderLayout.SOUTH);
        // Добавляем JScrollPane на панель содержимого окна
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // слушатель ОКНА со списком команд при ЗАКРЫТИИ окна
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setUiWork(false);
                //setVisible(false); // всё работает и без ЭТОЙ строки, но на всякий пожарный
            }
        });
        setServerWork(false);
        setUiWork(true);
        setVisible(true);
    }


    // вывод ЛЮБОГО сообщения в JTextArea logConsole с временной отметкой
    @Override
    public String printToConsole(String msg) {
        logConsole.append(msg + "\n");
        return msg;
    }


    @Override
    // МЕХАНИЗМ OBSERVER-LISTENER: добавляем ПРЕЗЕНТЕР в слушатели
    // ввиду того, что ПРЕЗЕНТЕР implements реализует интерфейс PropertyChangeListener
    // мы можем во VIEW работать не с самим ПРЕЗЕНТЕРом, а с его АБСТРАКЦИЕЙ
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    @Override
    // МЕХАНИЗМ OBSERVER-LISTENER: благодаря этому механизму выносим обработку событий в ПРЕЗЕНТЕР
    public void setUiWork(boolean newUiWork) {
        support.firePropertyChange("uiWork", this.uiWork, newUiWork);
        this.uiWork = newUiWork;
    }


    @Override
    // МЕХАНИЗМ OBSERVER-LISTENER: благодаря этому механизму выносим обработку событий в ПРЕЗЕНТЕР
    public void setServerWork(boolean newServerWork) {
        support.firePropertyChange("serverWork", this.serverWork, newServerWork);
        this.serverWork = newServerWork;
    }

    // ИДЕЯ НАБЛЮДЕНИЯ ЗА ОКНОМ БЛАГОДАРЯ ПОТОКУ - ВАРИАНТ
    /*
    public boolean isServerWork() {
        return serverWork;
    }

     */

}
