package sem2_client_server.server;



//https://javarush.com/groups/posts/654-klassih-socket-i-serversocket-ili-allo-server-tih-menja-slihshishjh


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


public class ServerModel {

    private File historyFile = new File("src/main/java/sem2_client_server/server/msg_history.txt");
    private List<String> msgHistory = new ArrayList<>();
    private List<User> userList = new ArrayList<>();

    private DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    private DateFormat dateFormat1 = new SimpleDateFormat("dd.MM.yyyy");

    private boolean serverWork = false;


    public ServerModel() {
        // -----пока пусто-----
    }


    public void start(){
        serverWork = true;
    }

    public void stop(){
        // очистить список пользователей
        // сохранить базу сообщений в файл
        userList.clear();
        saveChatHistory();
        serverWork = false;
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
    // если файл доступен для чтения - загрузка из файла в список msgHistory
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

    // добавление пользователя в список
    // вызывается из клиента - клиент просит добавить себя в список
    // это происходит при очередном коннекте пользователя к серверу
    // попутно - загружается история сообщений и отправляется только одному подключившемуся пользователю
    public boolean addUser(User newUser){
        if (serverWork) {
            userList.add(newUser);
            return true;
        }
        else
            return false;
    }


    // удаление пользователя из списка
    // инициируется клиентом при отключении
    // происходит отправка сообщения ВСЕМ присутствующим о том, что пользователь отключился
    public boolean removeUser(User userToRemove) {
        if (serverWork && userList.contains(userToRemove)) {
            userList.remove(userToRemove);
            return true;
        }
        else {
            return false;
        }
    }


    // получение сервером сообщения от пользователя и отправка его ВСЕМ, кто в списке
    // сохранение сообщения в истории
    public void receiveMessage(User user, String msgText) {
        msgHistory.add("(" + dateFormat.format(new Date()) + ") " + user.getLogin() + ": " + msgText);
        saveChatHistory();
    }

    public String getHistory(){
        StringBuilder history = new StringBuilder();
        history.append("История сообщений ============\n");
        for (String s : msgHistory) {
            history.append(s);
            history.append("\n");
        }
        history.append("=====================================");
        return history.toString();
    }

}
