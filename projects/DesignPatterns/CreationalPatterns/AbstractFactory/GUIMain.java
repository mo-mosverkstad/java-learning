package DesignPatterns.CreationalPatterns.AbstractFactory;

public class GUIMain {

    public static void main(String[] args) {
        ApplicationView applicationView = createApplicationView(System.getProperty("os.name"));
        applicationView.render();
    }

    private static ApplicationView createApplicationView(String osName) {
        GUIFactory guiFactory = osName.contains("mac") ? new MacOsFactory() : new WindowsFactory();
        return new ApplicationView(guiFactory);
    }
}
