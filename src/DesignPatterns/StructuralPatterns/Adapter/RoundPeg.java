package DesignPatterns.StructuralPatterns.Adapter;

public class RoundPeg {
    private double radius;

    public RoundPeg() {}

    public RoundPeg(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }
    
    @Override
    public String toString() {
        return "RoundPeg{" +
                "radius=" + radius +
                '}';
    }
}
