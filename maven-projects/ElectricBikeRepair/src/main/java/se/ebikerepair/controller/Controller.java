package se.ebikerepair.controller;

import se.ebikerepair.model.RepairOrder;
import se.ebikerepair.model.RepairOrderDTO;
import se.ebikerepair.integration.RepairOrderRegistry;

public class Controller {
    protected final RepairOrderRegistry repairOrderRegistry;
    protected RepairOrder repairOrder;

    public Controller(RepairOrderRegistry repairOrderRegistry){
        this.repairOrderRegistry = repairOrderRegistry;
        this.repairOrder = null;
    }

    public RepairOrderDTO requestRepairOrder(String id) throws IllegalArgumentException{
        repairOrder = repairOrderRegistry.find(id);
        if (repairOrder == null) {
            throw new IllegalArgumentException("Repair order not found for id: " + id);
        }
        return repairOrder.toDTO();
    }
    
}
