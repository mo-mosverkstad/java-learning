package se.ebikerepair.startup;

import se.ebikerepair.controller.ControllerCreator;
import se.ebikerepair.integration.RegistryCreator;
import se.ebikerepair.printer.Printer;
import se.ebikerepair.view.View;

public class Main {
    public static void main(String[] args) {
        RegistryCreator registryCreator = new RegistryCreator();
        Printer printer = new Printer();
        ControllerCreator controllerCreator = new ControllerCreator(registryCreator, printer);
        View view = new View(controllerCreator);
        view.proceedReceptionActions();
    }

    /*
    public int add(int x, int y) {
        return x + y;
    }
    */
}
