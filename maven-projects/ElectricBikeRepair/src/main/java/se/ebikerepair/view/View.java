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

    public void proceedReceptionActions() {
        try {
            CustomerDTO foundCustomer = receptionistController.searchCustomer("0707654321");
            System.out.println(foundCustomer);

            String id = receptionistController.createRepairOrder(new ProblemDTO("Broken bike chain", foundCustomer.getBikes().get(0)));
            System.out.println(id);

            RepairOrderDTO repairOrderDTO = receptionistController.requestRepairOrder(id);
            System.out.println(repairOrderDTO);

            RepairOrderDTO repairOrderDTO2 = technicianController.requestRepairOrder(id);
            DiagnosticReportDTO diagnosticReportDTO = new DiagnosticReportDTO("Rusty bike chain");
            technicianController.createDiagnosticReport(diagnosticReportDTO);

            RepairOrderDTO repairOrderDTO3 = technicianController.requestRepairOrder(id);
            System.out.println(repairOrderDTO3);

            
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(ERROR_PREFIX + e.getMessage());
        }
    }
}
