package Utility;

public class Sleep {
    public static void sleep(int numSecs) {
        try {
            Thread.sleep(numSecs * 1000);
        } catch (InterruptedException ignore) { }
    }
}
