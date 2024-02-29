package sem5_philosophers.caseMy;

public class Logger extends Thread{

    public static synchronized void printLog(String msg){
        System.out.println(msg);
    }

    @Override
    public void run() {
        super.run();
    }
}
