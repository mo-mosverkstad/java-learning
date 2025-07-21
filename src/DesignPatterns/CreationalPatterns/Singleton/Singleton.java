package DesignPatterns.CreationalPatterns.Singleton;

import java.util.Random;

public class Singleton {

    private static Singleton instance = null;
    private String value;

    private Singleton(String value) {
        this.value = value;
    }

    public static Singleton getInstance(String value) {
        try {
            Random random = new Random();
            Thread.sleep(random.nextInt(1000));
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        
        if (instance == null) {
            instance = new Singleton(value);
        }
        return instance;
    }

    public String getValue() {
        return value;
    }
}
