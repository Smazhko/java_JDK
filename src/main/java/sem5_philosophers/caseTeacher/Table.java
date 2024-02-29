package sem5_philosophers.caseTeacher;


public class Table {
    private final static int SITS_COUNT = 5;
    Philosopher[] philosophers = new Philosopher[SITS_COUNT];
    final Fork[] forks = new Fork[SITS_COUNT];

    public Table() {
        for (int i = 0; i < SITS_COUNT; i++) {
            forks[i] = new Fork();
        }
        for (int i = 0; i < SITS_COUNT; i++) {
            philosophers[i] = new Philosopher(this);
            int stick1index = i;
            int stick2index = (i == SITS_COUNT - 1) ? 0 : i + 1;
            philosophers[i].setLeftFork(forks[stick1index]);
            philosophers[i].setRightFork(forks[stick2index]);
        }
    }

    public void start(){
        for (int i = 0; i < SITS_COUNT; i++)
            philosophers[i].start();
    }


    public synchronized boolean isForksAvailable(Fork chopstick1, Fork chopstick2){
            if (chopstick1.isAvailable() && chopstick2.isAvailable()) {
                chopstick1.setAvailable(false);
                chopstick2.setAvailable(false);
                return true;
            }
        return false;
    }

    public synchronized void returnForks(Fork chopstick1, Fork chopstick2) {
        chopstick1.setAvailable(true);
        chopstick2.setAvailable(true);
    }
}
