package se.ebikerepair.view;

import se.ebikerepair.controller.ControllerCreator;
import se.ebikerepair.controller.ReceptionistController;
import se.ebikerepair.controller.TechnicianController;
import se.ebikerepair.integration.CustomerDTO;
import se.ebikerepair.integration.NonExistentTelephoneNumberException;
import se.ebikerepair.integration.BikeDTO;
import se.ebikerepair.integration.ProblemDTO;
import se.ebikerepair.integration.RepairOrderDTO;
import se.ebikerepair.integration.RepairTaskDTO;
import se.ebikerepair.model.Cost;
import se.ebikerepair.util.InvalidTelephoneNumberException;
import se.ebikerepair.integration.ResultDTO;
import se.ebikerepair.controller.FailedOperationException;

/**
 * View layer that orchestrates the repair workflow: reception preparation, technician diagnostics,
 * and reception confirmation. Catches and displays user-facing errors.
 */
public class View {
    private static final String ERROR_PREFIX = "Error: ";
    private final ReceptionistController receptionistController;
    private final TechnicianController technicianController;

    /**
     * Creates a view with controllers from the given controller creator.
     *
     * @param controllerCreator the factory providing access to controllers
     */
    public View(ControllerCreator controllerCreator) {
        receptionistController = controllerCreator.getReceptionistController();
        technicianController = controllerCreator.getTechnicianController();
    }

    /**
     * Runs the full repair workflow: reception preparation, technician diagnostics, and reception confirmation.
     *
     * @param telephoneNumber the customer's telephone number
     * @param bikeSerialNumber the serial number of the broken bike
     */
    public void proceedActions(String telephoneNumber, String bikeSerialNumber) {
        try{
            proceedReceptionPreparationActions(telephoneNumber, bikeSerialNumber);
            proceedTechnicianDiagnosticActions(telephoneNumber);
            proceedReceptionConfirmationActions(telephoneNumber);
        } catch (NonExistentTelephoneNumberException | InvalidTelephoneNumberException | FailedOperationException | IllegalArgumentException | IllegalStateException e) {
            System.out.println(ERROR_PREFIX + e.getMessage());
        }
    }

    private void proceedReceptionPreparationActions(String telephoneNumber, String bikeSerialNumber) throws NonExistentTelephoneNumberException, InvalidTelephoneNumberException, FailedOperationException {
        CustomerDTO foundCustomer = receptionistController.searchCustomer(telephoneNumber);
        printout("1. Reception - Found customer:", foundCustomer);

        BikeDTO foundBike = foundCustomer.getBikeBySerialNumber(bikeSerialNumber);
        String repairOrderId = receptionistController.createRepairOrder(telephoneNumber, new ProblemDTO("Broken bike chain", foundBike));
        printout("2. Reception - Created repair order with id: ", repairOrderId);

    }

    private void proceedTechnicianDiagnosticActions(String telephoneNumber) throws NonExistentTelephoneNumberException, InvalidTelephoneNumberException {
        RepairOrderDTO repairOrderDTO = technicianController.findRepairOrder(telephoneNumber);
        printout("3. Technician - Requested repair order: ", repairOrderDTO);

        String repairOrderId = repairOrderDTO.id();
        String diagnosticTaskName = "Mechanical Safety Check";
        ResultDTO result = new ResultDTO(true, true, "Chain and gears should be replaced.");
        technicianController.addDiagnosticResult(repairOrderId, diagnosticTaskName, result);
        printout("4. Technician - Updated diagnostic task: ", diagnosticTaskName);

        RepairTaskDTO repairTask1 = new RepairTaskDTO("Replace Chain", "Removal of worn or stretched chain and installation of a new compatible e\u2011bike chain. Includes lubrication, tension adjustment, and drivetrain alignment check.", new Cost(500, "SEK"), 1);
        RepairTaskDTO repairTask2 = new RepairTaskDTO("Replace gears", "Replacement of worn or damaged rear cassette/freewheel or front chainrings. Includes removal of old components, installation of new gear set, indexing and tuning of derailleur(s).", new Cost(400, "SEK"), 2);
        technicianController.addRepairTask(repairOrderId, repairTask1);
        technicianController.addRepairTask(repairOrderId, repairTask2);
        printout("5. Technician - Created proposed repair tasks 01: ", repairTask1);
        printout("6. Technician - Created proposed repair tasks 02: ", repairTask2);
    }

    private void proceedReceptionConfirmationActions(String telephoneNumber) throws NonExistentTelephoneNumberException, InvalidTelephoneNumberException {
        RepairOrderDTO repairOrderDTO = receptionistController.findRepairOrder(telephoneNumber);
        printout("7. Reception - found repair order: ", repairOrderDTO);

        printout("8. Reception - Accepted order");
        String repairOrderId = repairOrderDTO.id();
        receptionistController.acceptRepairOrder(repairOrderId);
    }

    private void printout(Object... objects) {
        for (Object object : objects) {
            System.out.println(object);
        }
        System.out.println();
    }
}
