package DesignPatterns.StructuralPatterns.Composite;

import java.util.ArrayList;
import java.util.List;

public class Box implements Component {
    private static final String IDENTATION_STRING = "  ";

    private List<Component> components = new ArrayList<>();
    
    public Box() {
        
    }

    public Box add(Component component) {
        components.add(component);
        return this;
    }

    public Box remove(Component component) {
        components.remove(component);
        return this;
    }

    @Override
    public void display() {
        System.out.println("Box");
        components.forEach((element) -> element.display(1));
    }

    public void display(int identationLevel){
        String ident = IDENTATION_STRING.repeat(identationLevel);
        System.out.print(ident);
        System.out.println("Box");
        components.forEach((element) -> element.display(identationLevel + 1));
    }
}
