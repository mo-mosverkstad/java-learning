package DesignPatterns.StructuralPatterns.MyComposite;

public class MyComponentMain {

    public static void main(String[] args) {
        MyComponent mySubComponent = new MyComponent("My Component 2").add(new MyComponent("My Component 2.1"));
        mySubComponent.display(1);
        System.out.println();

        MyComponent myComponent = new MyComponent("My Component")
                .add(new MyComponent("My Component 1"))
                .add(mySubComponent)
                .add(new MyComponent("My Component 3"));
        myComponent.display(0);
    }
}
