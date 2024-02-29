package sem5_philosophers.caseTeacher;

public class Fork {
    private static int counter = 1;
    private final int number;
    private volatile boolean available = true;

    public Fork() {
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


    public void setAvailable(boolean available) {
        this.available = available;
    }
}
