package se.ebikerepair.view;

import java.util.List;

import se.ebikerepair.controller.ControllerCreator;
import se.ebikerepair.controller.ReceptionistController;
import se.ebikerepair.controller.TechnicianController;
import se.ebikerepair.integration.CustomerDTO;
import se.ebikerepair.integration.BikeDTO;
import se.ebikerepair.model.DiagnosticReportDTO;
import se.ebikerepair.model.ProblemDTO;
import se.ebikerepair.model.ProposedRepairTaskDTO;
import se.ebikerepair.model.RepairOrderDTO;
import se.ebikerepair.model.Cost;

public class View {
    private static final String ERROR_PREFIX = "Error: ";
    private final ReceptionistController receptionistController;
    private final TechnicianController technicianController;

    public View(ControllerCreator controllerCreator){
        receptionistController = controllerCreator.getReceptionistController();
        technicianController = controllerCreator.getTechnicianController();
    }

    public void proceedActions(String telephoneNumber, String bikeSerialNumber) {
        proceedReceptionPreparationActions(telephoneNumber, bikeSerialNumber);
        proceedTechnicianDiagnosticActions(telephoneNumber);
        proceedReceptionConfirmationActions(telephoneNumber);
    }

    private void proceedReceptionPreparationActions(String telephoneNumber, String bikeSerialNumber) {
        try {
            CustomerDTO foundCustomer = receptionistController.searchCustomer(telephoneNumber);
            printout("1. Reception - Found customer:", foundCustomer);

            BikeDTO foundBike = foundCustomer.getBikeBySerialNumber(bikeSerialNumber);
            String repairOrderId = receptionistController.createRepairOrder(telephoneNumber, new ProblemDTO("Broken bike chain", foundBike));
            printout("2. Reception - Created repair order with id: ", repairOrderId);

        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(ERROR_PREFIX + e.getMessage());
        }
    }

    private void proceedTechnicianDiagnosticActions(String telephoneNumber){
        try {
            RepairOrderDTO repairOrderDTO = technicianController.findRepairOrder(telephoneNumber);
            printout("3. Technician - Requested repair order: ", repairOrderDTO);

            String repairOrderId = repairOrderDTO.id();
            DiagnosticReportDTO diagnosticReportDTO = new DiagnosticReportDTO("Rusty bike chain");
            technicianController.createDiagnosticReport(repairOrderId, diagnosticReportDTO);
            printout("4. Technician - Created diagnostic report: ", diagnosticReportDTO);

            ProposedRepairTaskDTO proposedRepairTaskDTO1 = new ProposedRepairTaskDTO("Replace chain", new Cost(500, "SEK"));
            ProposedRepairTaskDTO proposedRepairTaskDTO2 = new ProposedRepairTaskDTO("Replace gears", new Cost(400, "SEK"));
            technicianController.createProposedRepairTask(repairOrderId, proposedRepairTaskDTO1);
            technicianController.createProposedRepairTask(repairOrderId, proposedRepairTaskDTO2);
            printout("5. Technician - Created proposed repair tasks 01: ", proposedRepairTaskDTO1);
            printout("6. Technician - Created proposed repair tasks 02: ", proposedRepairTaskDTO2);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(ERROR_PREFIX + e.getMessage());
        }
    }

    private void proceedReceptionConfirmationActions(String telephoneNumber) {
        try {
            RepairOrderDTO repairOrderDTO = receptionistController.findRepairOrder(telephoneNumber);
            printout("7. Reception - found repair order: ", repairOrderDTO);
            
            printout("8. Reception - Accepted order");
            String repairOrderId = repairOrderDTO.id();
            receptionistController.acceptOrder(repairOrderId);
            
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(ERROR_PREFIX + e.getMessage());
        }
    }

    private void printout(Object... objects) {
        for (Object object : objects) {
            System.out.println(object);
        }
        System.out.println();
    }
}
