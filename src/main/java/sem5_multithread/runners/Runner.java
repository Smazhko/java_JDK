package sem5_multithread.runners;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Runner extends Thread{
    private String name;
    private static int number = 1;
    private Random rnd = new Random();
    private CountDownLatch cdl;

    public Runner(CountDownLatch cdl) {
        this.name = "Спортсмен " + number;
        this.cdl = cdl;
        number++;
    }

    @Override
    public void run() {
        try {
            goToStart();
            cdl.await(); // будем ждать, пока значение счётчика не станет равным нулю
            running();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void goToStart() throws InterruptedException {
        System.out.println(name + " выдвинулся на соревнования.");
        int time = rnd.nextInt(500, 2000);
        sleep(time);
        System.out.println(name + " прибыл к старту за " + time + " сек.");
        cdl.countDown();
    }

    public void running() throws InterruptedException {
        System.out.println(name + " стартовал.");
        int time = rnd.nextInt(500, 1500);
        sleep(time);
        System.out.println(name + " финишировал за " + time + " сек.");
    }

}
