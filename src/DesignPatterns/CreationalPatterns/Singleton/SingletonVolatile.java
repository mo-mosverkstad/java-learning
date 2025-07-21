package DesignPatterns.CreationalPatterns.Singleton;

public class SingletonVolatile {
    private volatile static SingletonVolatile instance = null;
    private String value;

    private SingletonVolatile(String value) {
        this.value = value;
    }

    public static SingletonVolatile getInstance(String value) {
        if (instance == null) {
            instance = new SingletonVolatile(value);
        }
        return instance;
    }

    public String getValue() {
        return value;
    }

}
