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

    public void createDiagnosticReport(String repairOrderId, DiagnosticReportDTO diagnosticReportDTO){
        RepairOrder repairOrder = repairOrderRegistry.findByRepairOrderId(repairOrderId);
        repairOrder.setDiagnosticReportDTO(diagnosticReportDTO);
        repairOrderRegistry.save(repairOrder);
    }

    public void createProposedRepairTask(String repairOrderId, ProposedRepairTaskDTO proposedRepairTaskDTO){
        RepairOrder repairOrder = repairOrderRegistry.findByRepairOrderId(repairOrderId);
        repairOrder.addProposedRepairTask(proposedRepairTaskDTO);
        repairOrderRegistry.save(repairOrder);
    }
}
