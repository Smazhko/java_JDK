package sem5_philosophers.caseMy;

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
        return available;
    }

    public void occupy(){
        this.available = false;
        Logger.printLog("    палочка " + number + " занята");
    }

    public void makeAvailable(){
        this.available = true;
        Logger.printLog("        палочка " + number + " освободилась");
    }

}
