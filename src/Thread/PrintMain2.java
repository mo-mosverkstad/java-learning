package Thread;

import Utility.Sleep;

public class PrintMain2 {

    public static int increment = 0;
    public static boolean lock = false;
    public static final String STRING_FORMAT_BEFPRE = "%s [before increment]: increment = %d";
    public static final String STRING_FORMAT_AFTER = "%s [after increment]: increment = %d";
    public static final String STRING_FORMAT_LOCKED = "%s LOCKED";
    public static final String STRING_FORMAT_UNLOCKED = "%s UNLOCKED";

    public static void main(String... args) {
        Runnable printMeRunnable = () -> {
            for (int i = 0; i < 5; i++) {
                System.out.println(String.format("%d, I (PrintMeRunnable) am printing... from %s", i, Thread.currentThread().getName()));
                PrintMain.increment(Thread.currentThread().getName());
                Sleep.sleep(1);
            }
        };
        // PrintMeThread printMeThread = new PrintMeThread();
        // new Thread(printMeRunnable).start();
        // new Thread(printMeThread).start();
        // System.out.println("Hello, World!");
        printMeRunnable.run();
    }

    public static void increment(String threadName) {
        //System.out.println(String.format(STRING_FORMAT_BEFPRE, threadName, increment));
        while (lock) {
            System.out.println(String.format("I (%s) am sleeping... for locked", threadName));
            Sleep.sleep(1);
        }
        lock = true;
        System.out.println(String.format(STRING_FORMAT_LOCKED, threadName));
        System.out.println(String.format(STRING_FORMAT_BEFPRE, threadName, increment));
        increment++;
        System.out.println(String.format(STRING_FORMAT_AFTER, threadName, increment));
        System.out.println(String.format(STRING_FORMAT_UNLOCKED, threadName));
        lock = false;
    }
}
