package se.ebikerepair.controller;

import se.ebikerepair.integration.RegistryCreator;

public class ControllerCreator {
    ReceptionistController receptionistController;
    TechnicianController technicianController;

    public ControllerCreator(RegistryCreator registryCreator){
        receptionistController = new ReceptionistController(registryCreator);
        technicianController = new TechnicianController(registryCreator);
    }

    public ReceptionistController getReceptionistController(){
        return receptionistController;
    }

    public TechnicianController getTechnicianController(){
        return technicianController;
    }
}
