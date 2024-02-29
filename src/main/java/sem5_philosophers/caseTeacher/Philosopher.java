package sem5_philosophers.caseTeacher;

import java.util.Random;

public class Philosopher extends Thread {
    private static int counter = 1;
    private final int number;
    private final Table table;
    private Fork leftFork;
    private Fork rightFork;
    private final Random rnd = new Random();
    private static final int EATS_COUNT = 3;
    private int minInterval = 1;
    private int maxInterval = 3;
    private int currentEat = 0;

    public Philosopher(Table table) {
        number = counter;
        counter++;
        this.table = table;
    }


    //@Override
    public void run0() {
        for (int i = 0; i < EATS_COUNT; i++) {
         //while (currentEat < EATS_COUNT){
            tryToEat();
            if (currentEat < EATS_COUNT - 1){
                try {
                    sleep(rnd.nextInt(minInterval, maxInterval));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    public void tryToEat0(){
        boolean continueFlag = true;
        while (continueFlag)
            if (table.isForksAvailable(leftFork, rightFork)) {
                continueFlag = false;
                eat();
            }
    }



    public void tryToEat1(){
        synchronized (leftFork){
            if (rightFork.isAvailable()){
                synchronized (rightFork) {
                    eat();
                }
            }
            else System.out.println(this + " вилок не дождался - ждёт дальше");
        }
    }


    public void run() {
        //for (int i = 0; i < EATS_COUNT; i++) {
        while (currentEat < EATS_COUNT){
            tryToEat();
            if (currentEat < EATS_COUNT - 1){
                try {
                    sleep(rnd.nextInt(minInterval, maxInterval));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void tryToEat(){
        if (table.isForksAvailable(leftFork, rightFork))
            eat();
    }


    public void eat(){
        currentEat += 1;
        printLog(this + " проголодался и " + currentEat + "-й раз принялся за свою лапшу, заняв " + showBothChopsticks()+".");
        try{
            sleep(rnd.nextInt(800, 1500));
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        leftFork.setAvailable(true);
        rightFork.setAvailable(true);
        if(currentEat < EATS_COUNT)
            printLog("   " + this + " поел, отложил " + showBothChopsticks() + " и начал размышлять...");
        else
            printLog("     " + this + " наелся досыта, " + showBothChopsticks() + " свободны.");
    }

    public void setLeftFork(Fork stick1) {
        this.leftFork = stick1;
    }

    public void setRightFork(Fork rightFork) {
        this.rightFork = rightFork;
    }

    public String showBothChopsticks() {
        return "вилки " + leftFork + " и " + rightFork;
    }

    @Override
    public String toString() {
        return "Философ " + number;
    }

    private void printLog(String msg){
        System.out.println(msg);
    }
}
