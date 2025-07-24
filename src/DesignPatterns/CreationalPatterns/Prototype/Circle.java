package DesignPatterns.CreationalPatterns.Prototype;

public class Circle extends ShapeBase {
    
    private int radius;

    public Circle(int x, int y, int radius, String color) {
        super(x, y, color);
        this.radius = radius;
    }

    public Circle(Circle target) {
        super(target);
        if (target != null) {
            this.radius = target.getRadius();
        }
    }

    public int getRadius() {
        return radius;
    }

    @Override
    public Shape clone() {
        return new Circle(this);
    }

    @Override
    public boolean equals(Object object2) {
        if (!(object2 instanceof Circle) || !super.equals(object2)) return false;
        Circle shape2 = (Circle) object2;
        return shape2.radius == radius;
    }
}
