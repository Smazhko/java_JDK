package sem5_philosophers;


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
        System.out.printf("%90s | 1 | 2 | 3 | 4 | 5 |%n", "Наличие палочек на столе ");

        for (int i = 0; i < SITS_COUNT; i++)
            philosophers[i].start();
    }

    public synchronized String getInfo(){
        StringBuilder result = new StringBuilder();
        result.append("| ");
            for (int i = 0; i < SITS_COUNT; i++)
                result.append(sticks[i].isAvailable() ? "+" : "-").append(" | ");
        return result.toString();
        //return "";
    }

    public synchronized boolean isChopsticksAvailable(Chopstick chopstick1, Chopstick chopstick2){
            if (chopstick1.isAvailable() && chopstick2.isAvailable()) {
                chopstick1.setAvailable(false);
                chopstick2.setAvailable(false);
                return true;
            }
        return false;
    }

    public synchronized void returnChopsticks(Chopstick chopstick1, Chopstick chopstick2) {
        chopstick1.setAvailable(true);
        chopstick2.setAvailable(true);
    }
}
