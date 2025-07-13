package Thread;

import Utility.Sleep;

public class PrintMeRunnable implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(String.format("%d, I (PrintMeRunnable) am printing... from %s", i, Thread.currentThread().getName()));
            PrintMain.increment(Thread.currentThread().getName());
            Sleep.sleep(1);
        }
    }

}
