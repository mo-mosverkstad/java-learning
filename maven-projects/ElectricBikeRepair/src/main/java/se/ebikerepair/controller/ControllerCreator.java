package se.ebikerepair.controller;

import se.ebikerepair.integration.RegistryCreator;
import se.ebikerepair.printer.Printer;

public class ControllerCreator {
    private final ReceptionistController receptionistController;
    private final TechnicianController technicianController;

    public ControllerCreator(RegistryCreator registryCreator, Printer printer){
        receptionistController = new ReceptionistController(registryCreator, printer);
        technicianController = new TechnicianController(registryCreator);
    }

    public ReceptionistController getReceptionistController(){
        return receptionistController;
    }

    public TechnicianController getTechnicianController(){
        return technicianController;
    }
}
