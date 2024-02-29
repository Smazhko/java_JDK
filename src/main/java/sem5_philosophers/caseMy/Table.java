package sem5_philosophers.caseMy;

public class Table {
    private final static int SITS_COUNT = 5;
    Philosopher[] philosophers = new Philosopher[SITS_COUNT];
    final Chopstick[] sticks = new Chopstick[SITS_COUNT];

    public Table() {
        System.out.println("Сервируем стол...");
        for (int i = 0; i < SITS_COUNT; i++) {
            sticks[i] = new Chopstick();
        }
        System.out.println("Рассаживаем гостей...");
        for (int i = 0; i < SITS_COUNT; i++) {
            philosophers[i] = new Philosopher(this);
            int stick1index = i;
            int stick2index = (i == SITS_COUNT - 1) ? 0 : i + 1;
            philosophers[i].setChopstick1(sticks[stick1index]);
            philosophers[i].setChopstick2(sticks[stick2index]);

            System.out.println(philosophers[i] +
                    " уселся за стол. Рядом с ним лежат " +
                    philosophers[i].showBothChopsticks());
        }
    }

    public void start(){
        for (int i = 0; i < SITS_COUNT; i++)
            philosophers[i].start();
    }


    public synchronized boolean isChopsticksAvailable(Chopstick chopstick1, Chopstick chopstick2){
            if (chopstick1.isAvailable() && chopstick2.isAvailable()) {
                chopstick1.occupy();
                chopstick2.occupy();
                return true;
            }
        return false;
    }

}
