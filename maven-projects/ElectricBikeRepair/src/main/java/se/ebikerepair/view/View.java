package se.ebikerepair.view;

import se.ebikerepair.controller.ControllerCreator;
import se.ebikerepair.controller.ReceptionistController;
import se.ebikerepair.controller.TechnicianController;
import se.ebikerepair.integration.CustomerDTO;
import se.ebikerepair.model.DiagnosticReportDTO;
import se.ebikerepair.model.ProblemDTO;
import se.ebikerepair.model.RepairOrderDTO;

public class View {
    private static final String ERROR_PREFIX = "Error: ";
    private final ReceptionistController receptionistController;
    private final TechnicianController technicianController;

    public View(ControllerCreator controllerCreator){
        receptionistController = controllerCreator.getReceptionistController();
        technicianController = controllerCreator.getTechnicianController();
    }

    public String proceedReceptionPreparationActions() {
        String repairOrderId = null;
        try {
            CustomerDTO foundCustomer = receptionistController.searchCustomer("0707654321");
            System.out.println(foundCustomer);

            repairOrderId = receptionistController.createRepairOrder(new ProblemDTO("Broken bike chain", foundCustomer.getBikes().get(0)));
            System.out.println(repairOrderId);

            RepairOrderDTO repairOrderDTO = receptionistController.requestRepairOrder(repairOrderId);
            System.out.println(repairOrderDTO);
            return repairOrderId;
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(ERROR_PREFIX + e.getMessage());
        }
        return repairOrderId;
    }

    public void proceedTechnicianDiagnosticActions(String repairOrderId){
        try {
            RepairOrderDTO repairOrderDTO = technicianController.requestRepairOrder(repairOrderId);
            DiagnosticReportDTO diagnosticReportDTO = new DiagnosticReportDTO("Rusty bike chain");
            technicianController.createDiagnosticReport(diagnosticReportDTO);

        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(ERROR_PREFIX + e.getMessage());
        }
    }

    public void proceedReceptionConfirmationActions(String repairOrderId) {
        try {
            RepairOrderDTO repairOrderDTO = receptionistController.requestRepairOrder(repairOrderId);
            System.out.println(repairOrderDTO);
            
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(ERROR_PREFIX + e.getMessage());
        }
    }
}
