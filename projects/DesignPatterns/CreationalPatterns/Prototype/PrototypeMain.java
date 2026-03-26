package DesignPatterns.CreationalPatterns.Prototype;

public class PrototypeMain {

    public static void main(String[] args) {
        Shape circle = new Circle(5, 5, 10, "red");
        Shape clone = circle.clone();
        System.out.println(circle.equals(clone));

        Shape rectangle = new Rectangle(5, 5, 10, 20, "blue");
        Shape clone2 = rectangle.clone();
        System.out.println(rectangle.equals(clone2));
    }
}
