package Sorting;

public class Car {
    private String name;
    private int producedYear;

    public Car(String name, int producedYear) {
        this.name = name;
        this.producedYear = producedYear;
    }

    public int getProducedYear() {
        return producedYear;
    }

    @Override
    public String toString() {
        return name + " : " + producedYear;
    }
}
