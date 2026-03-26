package se.ebikerepair.view;

import se.ebikerepair.controller.ControllerCreator;
import se.ebikerepair.controller.ReceptionistController;
import se.ebikerepair.integration.CustomerDTO;
import se.ebikerepair.model.ProblemDTO;

public class View {
    public View(ControllerCreator controllerCreator){
        ReceptionistController receptionistController = controllerCreator.getReceptionistController();
        CustomerDTO foundCustomer = receptionistController.searchCustomer("0707654321");
        System.out.println(foundCustomer);

        String id = receptionistController.createRepairOrder(new ProblemDTO("Description...", foundCustomer.getBikes().get(0)));

        System.out.println(id);
    }
}
