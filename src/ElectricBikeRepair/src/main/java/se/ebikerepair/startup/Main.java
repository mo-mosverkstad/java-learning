package se.ebikerepair.startup;

import se.ebikerepair.controller.ControllerCreator;
import se.ebikerepair.integration.RegistryCreator;
import se.ebikerepair.printer.Printer;
import se.ebikerepair.view.View;

public class Main {
    public static void main(String[] args) {
        RegistryCreator registryCreator = new RegistryCreator();
        Printer printer = new Printer();
        ControllerCreator controllerCreator = new ControllerCreator(registryCreator);
        View view = new View(controllerCreator);
        // System.out.println("Hello World!");
    }

    /*
    public int add(int x, int y) {
        return x + y;
    }
    */
}
