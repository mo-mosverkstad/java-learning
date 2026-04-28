package se.ebikerepair.controller;

import se.ebikerepair.integration.NoExistedRepairOrderException;
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
     * @throws NoExistedRepairOrderException if the repair order is not found
     */
    public void addDiagnosticResult(String repairOrderId, String diagnosticTaskName, ResultDTO result) throws NoExistedRepairOrderException {
        RepairOrder repairOrder = repairOrderRegistry.findByRepairOrderId(repairOrderId);
        repairOrder.updateDiagnosticResult(diagnosticTaskName, result);
        repairOrderRegistry.save(repairOrder);
    }

    /**
     * Adds a repair task to a repair order.
     *
     * @param repairOrderId the ID of the repair order
     * @param repairTask the repair task to add
     * @throws NoExistedRepairOrderException if the repair order is not found
     */
    public void addRepairTask(String repairOrderId, RepairTaskDTO repairTask) throws NoExistedRepairOrderException {
        RepairOrder repairOrder = repairOrderRegistry.findByRepairOrderId(repairOrderId);
        repairOrder.addRepairTask(repairTask);
        repairOrderRegistry.save(repairOrder);
    }
}
