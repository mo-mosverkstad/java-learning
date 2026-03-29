package se.ebikerepair.startup;

import se.ebikerepair.controller.ControllerCreator;
import se.ebikerepair.integration.RegistryCreator;
import se.ebikerepair.integration.Printer;
import se.ebikerepair.view.View;

/**
 * Application entry point. Initializes registries, controllers, and view, then runs the repair workflow.
 */
public class Main {
    /**
     * Starts the electric bike repair application.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        RegistryCreator registryCreator = new RegistryCreator();
        Printer printer = new Printer();
        ControllerCreator controllerCreator = new ControllerCreator(registryCreator, printer);
        View view = new View(controllerCreator);
        view.proceedActions("0707654321", "SK-2024-055");
    }
}
