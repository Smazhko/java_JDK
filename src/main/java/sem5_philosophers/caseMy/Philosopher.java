package sem5_philosophers.caseMy;

import java.util.Random;

public class Philosopher extends Thread {
    private static int counter = 1;
    private final int number;
    private final Table table;
    private Chopstick chopstick1;
    private Chopstick chopstick2;
    private final Random rnd = new Random();
    private static final int EATS_COUNT = 3;
    private int minInterval = 100;
    private int maxInterval = 1000;
    private int currentEat = 0;
    private static final String[] color = new String[]{
            "\u001B[91m", "\u001B[92m", "\u001B[93m", "\u001B[94m", "\u001B[95m", "\u001B[96m", "\u001B[97m"};


    public Philosopher(Table table) {
        number = counter;
        counter++;
        this.table = table;
    }




    // таким методом мы пытаемся завладеть палочками по очереди,
    // что может привести к DeadLock - каждый философ возьмёт по палочке
    // и будет ждать вторую. бесконечно.
//    public void tryToEat1(){
//        synchronized (chopstick1){
//            if (chopstick2.isAvailable()){
//                synchronized (chopstick2) {
//                    eat();
//                }
//            }
//            else System.out.println(this + " палочек не дождался - ждёт дальше");
//        }
//    }


    @Override
    public void run() {
        for (int i = 0; i < EATS_COUNT; i++) {
            tryToEat();
            if (currentEat < EATS_COUNT - 1){
                think();
            }
        }
    }


    public void tryToEat(){
        boolean continueFlag = true;
        while (continueFlag) {
            if (table.isChopsticksAvailable(chopstick1, chopstick2)) {
                continueFlag = false;
                currentEat += 1;
                printLog(this + " проголодался и " + currentEat + "-й раз взялся за свой рамен, заняв " + showBothChopsticks() + ".");

                try {
                    sleep(rnd.nextInt(minInterval, maxInterval));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (currentEat < EATS_COUNT)
                    printLog("   " + this + " поел, отложил " + showBothChopsticks() + " и начал размышлять...");
                else
                    printLog("     " + this + " наелся досыта, " + showBothChopsticks() + " свободны. Ничего не сказав, он ушёл в закат.");

                chopstick1.makeAvailable();
                chopstick2.makeAvailable();
            }
        }
    }

    private void think(){
        try {
            sleep(rnd.nextInt(minInterval, maxInterval));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    public void setChopstick1(Chopstick stick1) {
        this.chopstick1 = stick1;
    }

    public void setChopstick2(Chopstick chopstick2) {
        this.chopstick2 = chopstick2;
    }

    public String showBothChopsticks() {
        return "палочки " + chopstick1 + " и " + chopstick2;
    }

    @Override
    public String toString() {
        return "Философ " + number;
    }

    private void printLog(String msg){
        Logger.printLog(color[number - 1] + msg + "\u001B[0m");
    }
}
