package DesignPatterns.CreationalPatterns.AbstractFactory;

public class MacOsFactory implements GUIFactory {

    public Button createButton() {
        return new MacOsButton();
    }

    public CheckBox createCheckBox() {
        return new MacOsCheckBox();
    }
}
