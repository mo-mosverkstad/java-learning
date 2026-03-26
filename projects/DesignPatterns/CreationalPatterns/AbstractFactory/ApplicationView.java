package DesignPatterns.CreationalPatterns.AbstractFactory;

public class ApplicationView {

    private final GUIFactory guiFactory;

    public ApplicationView(GUIFactory guiFactory) {
        this.guiFactory = guiFactory;
    }

    public void render() {
        Button button = guiFactory.createButton();
        CheckBox checkBox = guiFactory.createCheckBox();
        button.paint();
        checkBox.paint();
    }
}
