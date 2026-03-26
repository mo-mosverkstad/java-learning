package DesignPatterns.StructuralPatterns.Composite;

public class CompositeMain {

    public static void main(String[] args) {
        Box box = new Box();
        box.add(new Product("Product 1"));
        box.add(new Product("Product 2"));
        box.add(new Box().add(new Product("Product 1.1")).add(new Product("Product 1.2")));
        box.add(new Product("Product 3"));
        box.display();
    }
}
