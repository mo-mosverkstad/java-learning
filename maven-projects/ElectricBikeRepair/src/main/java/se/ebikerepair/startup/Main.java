package se.ebikerepair.startup;

import se.ebikerepair.util.LogHandler;
import se.ebikerepair.controller.ControllerCreator;
import se.ebikerepair.integration.RegistryCreator;
import se.ebikerepair.integration.Printer;
import se.ebikerepair.view.View;

/**
 * 
 * Application entry point. Initializes registries, controllers, and view, then runs the repair workflow.
 */
public class Main {
    /**
     * Starts the electric bike repair application.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        try{
            RegistryCreator registryCreator = new RegistryCreator();
            Printer printer = new Printer();
            ControllerCreator controllerCreator = new ControllerCreator(registryCreator, printer);
            View view = new View(controllerCreator);
            // Normal main use case scenario
            view.proceedActions("0707654321", "SK-2024-055");
            // view.proceedActions("0701234567", "CR-2024-001");
            // view.proceedActions("0701112233", "KR-2024-100");

            // Negative use case, this telephone number could not be used to find the customer
            // view.proceedActions("0707654322", "SK-2024-055");

            // Negative use case, this telephone number format is invalid
            // view.proceedActions("11707654322", "SK-2024-055");
        }
        catch (RuntimeException exception){
            LogHandler.getLogger().logException(exception);
        }
    }
}
