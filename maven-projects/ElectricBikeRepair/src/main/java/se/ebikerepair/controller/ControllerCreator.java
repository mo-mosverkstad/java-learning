package se.ebikerepair.controller;

import se.ebikerepair.integration.RegistryCreator;
import se.ebikerepair.printer.Printer;

/**
 * Factory class that creates and provides access to all controllers.
 */
public class ControllerCreator {
    private final ReceptionistController receptionistController;
    private final TechnicianController technicianController;

    /**
     * Creates all controllers using the given registry creator and printer.
     *
     * @param registryCreator the creator providing access to data registries
     * @param printer the printer for printing repair orders
     */
    public ControllerCreator(RegistryCreator registryCreator, Printer printer){
        receptionistController = new ReceptionistController(registryCreator, printer);
        technicianController = new TechnicianController(registryCreator);
    }

    /**
     * Returns the receptionist controller.
     *
     * @return the receptionist controller
     */
    public ReceptionistController getReceptionistController(){
        return receptionistController;
    }

    /**
     * Returns the technician controller.
     *
     * @return the technician controller
     */
    public TechnicianController getTechnicianController(){
        return technicianController;
    }
}
