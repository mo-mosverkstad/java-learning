package DesignPatterns.CreationalPatterns.Prototype;

public class Rectangle extends ShapeBase {
    private int width;
    private int height;

    public Rectangle(int x, int y, int width, int height, String color) {
        super(x, y, color);
        this.width = width;
        this.height = height;
    }

    public Rectangle(Rectangle target) {
        super(target);
        if (target != null) {
            this.width = target.getWidth();
            this.height = target.getHeight();
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public Shape clone() {
        return new Rectangle(this);
    }

    @Override
    public boolean equals(Object object2) {
        if (!(object2 instanceof Rectangle) || !super.equals(object2)) return false;
        Rectangle shape2 = (Rectangle) object2;
        return shape2.getWidth() == width && shape2.getHeight() == height;
    }

}
