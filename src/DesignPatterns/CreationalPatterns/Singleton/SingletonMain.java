package DesignPatterns.CreationalPatterns.Singleton;

public class SingletonMain {

    private static String value1, value2;
    public static void main(String[] args) {
        boolean result = true;
        for (int i = 0; i < 100 && result; i++) {
            result = result && verify();
            System.out.println(String.format("%d) %s", i, result));
        }
        System.out.println(result);
    }

    private static boolean verify() {

        Thread thread1 = new Thread(() -> {
            SingletonVolatile singleton = SingletonVolatile.getInstance("First");
            // System.out.println(singleton);
            // System.out.println(singleton.getValue()); 
            value1 = singleton.getValue();
        });

        Thread thread2 = new Thread(() -> {
            SingletonVolatile singleton = SingletonVolatile.getInstance("Second");
            // System.out.println(singleton);
            // System.out.println(singleton.getValue());
            value2 = singleton.getValue();
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return value1.equals(value2);
    }
}
