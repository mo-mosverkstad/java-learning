package DesignPatterns.StructuralPatterns.Adapter;

public class SquarePeg {
    private double width;
    public SquarePeg(double width) {
        this.width = width;
    }
    public double getWidth() {
        return width;
    }

    public double getArea() {
        return width * width;
    }

    @Override
    public String toString() {
        return "SquarePeg{" +
                "width=" + width +
                '}';
    }
}
