package DesignPatterns.CreationalPatterns.Prototype;

import java.util.Objects;

public abstract class ShapeBase implements Shape {
    private int x;
    private int y;
    private String color;

    public ShapeBase(int x, int y, String color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public ShapeBase(Shape target) {
        if (target != null) {
            this.x = target.getX();
            this.y = target.getY();
            this.color = target.getColor();
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getColor() {
        return color;
    }

    @Override
    public abstract Shape clone();

    @Override
    public boolean equals(Object object2) {
        if (!(object2 instanceof Shape)) return false;
        Shape shape2 = (Shape) object2;
        return shape2.getX() == x && shape2.getY() == y && Objects.equals(shape2.getColor(), color);
    }

}
