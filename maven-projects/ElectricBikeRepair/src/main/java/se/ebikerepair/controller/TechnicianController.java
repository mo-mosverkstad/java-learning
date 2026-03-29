package se.ebikerepair.controller;

import se.ebikerepair.integration.RegistryCreator;
import se.ebikerepair.integration.RepairOrderRegistry;
import se.ebikerepair.model.DiagnosticReportDTO;
import se.ebikerepair.model.ProposedRepairTaskDTO;
import se.ebikerepair.model.RepairOrder;
import se.ebikerepair.model.RepairOrderDTO;
import se.ebikerepair.model.ResultDTO;

/**
 * Controller handling technician operations: diagnostic reports, diagnostic results, and proposed repair tasks.
 */
public class TechnicianController extends Controller {
    /**
     * Creates a technician controller with access to registries.
     *
     * @param registryCreator the creator providing access to data registries
     */
    public TechnicianController(RegistryCreator registryCreator){
        super(registryCreator.getRepairOrderRegistry());
    }

    /**
     * Creates and attaches a diagnostic report to a repair order.
     *
     * @param repairOrderId the ID of the repair order
     * @param diagnosticReport the diagnostic report to attach
     * @throws IllegalStateException if the repair order is not found
     */
    public void createDiagnosticReport(String repairOrderId, DiagnosticReportDTO diagnosticReport) throws IllegalStateException{
        RepairOrder repairOrder = repairOrderRegistry.findByRepairOrderId(repairOrderId);
        if (repairOrder == null) {
            throw new IllegalStateException("Repair order not found for id: " + repairOrderId);
        }
        repairOrder.setDiagnosticReport(diagnosticReport);
        repairOrderRegistry.save(repairOrder);
    }

    /**
     * Adds a diagnostic result to a specific diagnostic task within a repair order.
     *
     * @param repairOrderId the ID of the repair order
     * @param diagnosticTaskIndex the index of the diagnostic task to update
     * @param result the result to apply to the diagnostic task
     * @throws IllegalStateException if the repair order is not found
     * @throws IndexOutOfBoundsException if the diagnostic task index is out of range
     */
    public void addDiagnosticResult(String repairOrderId, int diagnosticTaskIndex, ResultDTO result) throws IllegalStateException, IndexOutOfBoundsException{
        RepairOrder repairOrder = repairOrderRegistry.findByRepairOrderId(repairOrderId);
        if (repairOrder == null) {
            throw new IllegalStateException("Repair order not found for id: " + repairOrderId);
        }
        repairOrder.updateDiagnosticResult(diagnosticTaskIndex, result);
        repairOrder.calculateCostByDiagnosticTask(diagnosticTaskIndex);
        repairOrderRegistry.save(repairOrder);
    }

    /**
     * Adds a proposed repair task to a repair order and updates the total cost.
     *
     * @param repairOrderId the ID of the repair order
     * @param proposedRepairTaskDTO the proposed repair task to add
     * @throws IllegalStateException if the repair order is not found
     */
    public void addProposedRepairTask(String repairOrderId, ProposedRepairTaskDTO proposedRepairTaskDTO) throws IllegalStateException{
        RepairOrder repairOrder = repairOrderRegistry.findByRepairOrderId(repairOrderId);
        if (repairOrder == null) {
            throw new IllegalStateException("Repair order not found for id: " + repairOrderId);
        }
        repairOrder.addProposedRepairTask(proposedRepairTaskDTO);
        repairOrderRegistry.save(repairOrder);
    }
}
