package se.ebikerepair.controller;

import se.ebikerepair.integration.RegistryCreator;
import se.ebikerepair.model.RepairOrder;
import se.ebikerepair.integration.RepairTaskDTO;
import se.ebikerepair.integration.ResultDTO;

/**
 * Controller handling technician operations: diagnostic results and proposed repair tasks.
 */
public class TechnicianController extends Controller {
    /**
     * Creates a technician controller with access to registries.
     *
     * @param registryCreator the creator providing access to data registries
     */
    public TechnicianController(RegistryCreator registryCreator) {
        super(registryCreator.getRepairOrderRegistry());
    }

    /**
     * Adds a diagnostic result to a diagnostic task identified by name within a repair order.
     *
     * @param repairOrderId the ID of the repair order
     * @param diagnosticTaskName the name (partial match) of the diagnostic task to update
     * @param result the result to apply to the diagnostic task
     * @throws IllegalStateException if the repair order is not found
     * @throws IllegalArgumentException if no diagnostic task matches the given name
     */
    public void addDiagnosticResult(String repairOrderId, String diagnosticTaskName, ResultDTO result) throws IllegalStateException, IllegalArgumentException {
        RepairOrder repairOrder = repairOrderRegistry.findByRepairOrderId(repairOrderId);
        if (repairOrder == null) {
            throw new IllegalStateException("Repair order not found for id: " + repairOrderId);
        }
        repairOrder.getDiagnosticReport().updateDiagnosticResult(diagnosticTaskName, result);
        repairOrderRegistry.save(repairOrder);
    }

    /**
     * Adds a repair task to a repair order.
     *
     * @param repairOrderId the ID of the repair order
     * @param repairTask the repair task to add
     * @throws IllegalStateException if the repair order is not found
     */
    public void addRepairTask(String repairOrderId, RepairTaskDTO repairTask) throws IllegalStateException {
        RepairOrder repairOrder = repairOrderRegistry.findByRepairOrderId(repairOrderId);
        if (repairOrder == null) {
            throw new IllegalStateException("Repair order not found for id: " + repairOrderId);
        }

        repairOrder.getRepairTaskCollection().addRepairTask(repairTask);
        repairOrderRegistry.save(repairOrder);
    }
}
