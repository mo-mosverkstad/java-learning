package DesignPatterns.StructuralPatterns.Composite;

public class Product  implements Component {
    private static final String IDENTATION_STRING = "  ";

    private String name;

    public Product(String name) {
        this.name = name;
    }

    @Override
    public void display() {
        System.out.println(String.format("Product: %s", name));
    }

    @Override
    public void display(int identationLevel) {
        String ident = IDENTATION_STRING.repeat(identationLevel);
        System.out.println(String.format("%sProduct: %s", ident, name));
    }
}
