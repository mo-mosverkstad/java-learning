package Thread;

import Utility.Sleep;

public class PrintMeThread extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(String.format("%d, I (PrintMeThread) am printing... from %s", i, Thread.currentThread().getName()));
            PrintMain.increment(Thread.currentThread().getName());
            Sleep.sleep(1);
        }
    }
}
