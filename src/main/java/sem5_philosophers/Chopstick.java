package sem5_philosophers;

import java.util.concurrent.atomic.AtomicBoolean;

public class Chopstick {
    private static int counter = 1;
    private final int number;
    //private AtomicBoolean available = new AtomicBoolean(true);
    private volatile boolean available = true;

    public Chopstick() {
        number = counter;
        counter++;
    }


    @Override
    public String toString() {
        return "" + number;
    }


    public boolean isAvailable() {
        //return available.get();
        return available;
    }


    public void setAvailable(boolean available) {
        //this.available.set(available);
        this.available = available;
    }
}
