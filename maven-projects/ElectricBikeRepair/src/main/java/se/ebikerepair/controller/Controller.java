package se.ebikerepair.controller;

import se.ebikerepair.model.RepairOrder;
import se.ebikerepair.model.RepairOrderDTO;
import se.ebikerepair.integration.RepairOrderRegistry;
import se.ebikerepair.util.TelephoneNumber;
import java.util.List;
import java.util.ArrayList;

public class Controller {
    protected final RepairOrderRegistry repairOrderRegistry;
    // protected RepairOrder repairOrder;

    public Controller(RepairOrderRegistry repairOrderRegistry){
        this.repairOrderRegistry = repairOrderRegistry;
        // this.repairOrder = null;
    }

    public List<String> findRepairOrderIds(String telephoneNumber){
        String phoneNumberE164 = new TelephoneNumber(telephoneNumber).toE164();
        List<RepairOrder> repairOrders = repairOrderRegistry.findRepairOrdersByTelephoneNumber(phoneNumberE164);
        List<String> repairOrderIds = new ArrayList<>();
        for (RepairOrder repairOrder : repairOrders){
            repairOrderIds.add(repairOrder.getId());
        }
        return repairOrderIds;
    }

    public RepairOrderDTO requestRepairOrder(String id) throws IllegalArgumentException{
        RepairOrder repairOrder = repairOrderRegistry.findByRepairOrderId(id);
        if (repairOrder == null) {
            throw new IllegalArgumentException("Repair order not found for id: " + id);
        }
        return repairOrder.toDTO();
    }
    
}
