package DesignPatterns.CreationalPatterns.AbstractFactory;

public class WindowsButton implements Button {
    @Override
    public void paint() {
        System.out.println("Painting Windows Button");
    }

}
