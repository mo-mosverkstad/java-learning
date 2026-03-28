package se.ebikerepair.controller;

import se.ebikerepair.integration.RegistryCreator;
import se.ebikerepair.integration.RepairOrderRegistry;
import se.ebikerepair.model.DiagnosticReportDTO;
import se.ebikerepair.model.ProposedRepairTaskDTO;
import se.ebikerepair.model.RepairOrder;
import se.ebikerepair.model.RepairOrderDTO;
import se.ebikerepair.model.ResultDTO;

public class TechnicianController extends Controller {
    public TechnicianController(RegistryCreator registryCreator){
        super(registryCreator.getRepairOrderRegistry());
    }

    public void createDiagnosticReport(String repairOrderId, DiagnosticReportDTO diagnosticReport){
        RepairOrder repairOrder = repairOrderRegistry.findByRepairOrderId(repairOrderId);
        if (repairOrder == null) {
            throw new IllegalStateException("Repair order not found for id: " + repairOrderId);
        }
        repairOrder.setDiagnosticReport(diagnosticReport);
        repairOrderRegistry.save(repairOrder);
    }

    public void addDiagnosticResult(String repairOrderId, int diagnosticTaskIndex, ResultDTO result){
        RepairOrder repairOrder = repairOrderRegistry.findByRepairOrderId(repairOrderId);
        if (repairOrder == null) {
            throw new IllegalStateException("Repair order not found for id: " + repairOrderId);
        }
        repairOrder.getDiagnosticReport().getDiagnosticTasks().get(diagnosticTaskIndex).getResult().update(result);
        repairOrder.calculateCostByDiagnosticTask(diagnosticTaskIndex);
        repairOrderRegistry.save(repairOrder);
    }

    public void addProposedRepairTask(String repairOrderId, ProposedRepairTaskDTO proposedRepairTaskDTO){
        RepairOrder repairOrder = repairOrderRegistry.findByRepairOrderId(repairOrderId);
        if (repairOrder == null) {
            throw new IllegalStateException("Repair order not found for id: " + repairOrderId);
        }
        repairOrder.addProposedRepairTask(proposedRepairTaskDTO);
        repairOrderRegistry.save(repairOrder);
    }
}
