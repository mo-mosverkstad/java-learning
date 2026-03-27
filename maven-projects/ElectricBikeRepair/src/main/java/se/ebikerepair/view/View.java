package se.ebikerepair.view;

import se.ebikerepair.controller.ControllerCreator;
import se.ebikerepair.controller.ReceptionistController;
import se.ebikerepair.integration.CustomerDTO;
import se.ebikerepair.model.ProblemDTO;
import se.ebikerepair.model.RepairOrderDTO;

public class View {
    private static final String ERROR_PREFIX = "Error: ";
    private final ReceptionistController receptionistController;

    public View(ControllerCreator controllerCreator){
        receptionistController = controllerCreator.getReceptionistController();
    }

    public void proceedReceptionActions() {
        try {
            CustomerDTO foundCustomer = receptionistController.searchCustomer("0707654321");
            System.out.println(foundCustomer);

            String id = receptionistController.createRepairOrder(new ProblemDTO("Broken bike chain", foundCustomer.getBikes().get(0)));
            System.out.println(id);

            RepairOrderDTO repairOrderDTO = receptionistController.requestRepairOrder(id);
            System.out.println(repairOrderDTO);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(ERROR_PREFIX + e.getMessage());
        }
    }
}
