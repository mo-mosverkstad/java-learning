package se.ebikerepair.view;

import se.ebikerepair.controller.ControllerCreator;
import se.ebikerepair.controller.ReceptionistController;

public class View {
    public View(ControllerCreator controllerCreator){
        ReceptionistController receptionistController = controllerCreator.getReceptionistController();
        System.out.println(receptionistController.searchCustomer("0707654321"));
    }
}
