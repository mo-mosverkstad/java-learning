package se.ebikerepair.controller;

import se.ebikerepair.integration.RegistryCreator;
import se.ebikerepair.integration.RepairOrderRegistry;
import se.ebikerepair.model.DiagnosticReportDTO;
import se.ebikerepair.model.RepairOrder;
import se.ebikerepair.model.RepairOrderDTO;

public class TechnicianController {
    private final RepairOrderRegistry repairOrderRegistry;
    private RepairOrder repairOrder;

    public TechnicianController(RegistryCreator registryCreator){
        repairOrderRegistry = registryCreator.getRepairOrderRegistry();
    }

    public RepairOrderDTO requestRepairOrder(String id) throws IllegalArgumentException{
        repairOrder = repairOrderRegistry.find(id);
        if (repairOrder == null) {
            throw new IllegalArgumentException("Repair order not found for id: " + id);
        }
        return repairOrder.toDTO();
    }

    public void createDiagnosticReport(DiagnosticReportDTO diagnosticReportDTO){
        repairOrder.setDiagnosticReportDTO(diagnosticReportDTO);
        repairOrderRegistry.save(repairOrder);
    }
}
