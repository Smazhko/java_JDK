package sem5_multithread.runners;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class AnotherRunner extends Thread{
    private String name;
    private static int number = 1;
    private Random rnd = new Random();
    private CountDownLatch cdl;

    public AnotherRunner(CountDownLatch cdl) {
        this.name = "Спортсмен " + number;
        this.cdl = cdl;
        number++;
    }

    @Override
    public void run() {
        try {
            goToStart();
            synchronized (this){
                // this указывается для того, чтобы понимать,
                // кто должен будет уснуть - объект БЕГУН.
                // После того, как объект БЕГУН попадает в синхронизацию,
                // его монитор захватывается потоком главной(?) программы.
                // Объект БЕГУН ждёт "волшебного пинка" - notify().
                // Пока метод notify() не сработает, объект БЕГУН будет
                // заблокирован - будет спать.
                // Это значит, что ни у кого не будет доступа к синхронизированным
                // методам объекта БЕГУН. Поэтому НЕсинхронизированный метод go()
                // всё-таки можно будет выполнить.
                // wait по сути = sleep с условием.
                // wait и notify - парные методы и должны выполняться
                // внутри синхронизированного блока
                wait();
            }
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

    // метод вызывается извне, когда всем спортсменам
    // надо будет стортовать
    public void go(){
        synchronized (this){
            notify();
        }
    }

}
