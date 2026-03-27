package se.ebikerepair.controller;

import se.ebikerepair.integration.RegistryCreator;
import se.ebikerepair.integration.RepairOrderRegistry;
import se.ebikerepair.model.DiagnosticReportDTO;
import se.ebikerepair.model.ProposedRepairTaskDTO;
import se.ebikerepair.model.RepairOrder;
import se.ebikerepair.model.RepairOrderDTO;

public class TechnicianController extends Controller {
    public TechnicianController(RegistryCreator registryCreator){
        super(registryCreator.getRepairOrderRegistry());
    }

    public void createDiagnosticReport(DiagnosticReportDTO diagnosticReportDTO){
        repairOrder.setDiagnosticReportDTO(diagnosticReportDTO);
        repairOrderRegistry.save(repairOrder);
    }

    public void createProposedRepairTask(ProposedRepairTaskDTO proposedRepairTaskDTO){
        repairOrder.addProposedRepairTask(proposedRepairTaskDTO);
        repairOrderRegistry.save(repairOrder);
    }
}
