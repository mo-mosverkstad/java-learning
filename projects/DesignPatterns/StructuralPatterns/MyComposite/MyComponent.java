package DesignPatterns.StructuralPatterns.MyComposite;

import java.util.ArrayList;
import java.util.List;

public class MyComponent {
    private String name;
    private List<MyComponent> children = new ArrayList<>();

    public MyComponent(String name) {
        this.name = name;
    }

    public MyComponent add(MyComponent component) {
        children.add(component);
        return this;
    }

    public MyComponent remove(MyComponent component) {
        children.remove(component);
        return this;
    }

    public void display(int identationLevel) {
        String ident = "  ".repeat(identationLevel);
        System.out.println(ident + name);
        children.forEach((element) -> element.display(identationLevel + 1));
    }
}
