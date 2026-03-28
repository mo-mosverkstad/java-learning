package se.ebikerepair.integration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import se.ebikerepair.model.RepairOrder;

public class RepairOrderRegistry{
    private Map<String, RepairOrder> repairOrdersByRepairOrderId;
    private Map<String, List<RepairOrder>> repairOrdersByTelephoneNumber;

    public RepairOrderRegistry(){
        repairOrdersByRepairOrderId = new HashMap<>();
        repairOrdersByTelephoneNumber = new HashMap<>();
    }

    public List<RepairOrder> findRepairOrdersByTelephoneNumber(String telephoneNumber){
        return repairOrdersByTelephoneNumber.getOrDefault(telephoneNumber, Collections.emptyList());
    }

    public RepairOrder findByRepairOrderId(String repairOrderId){
        return repairOrdersByRepairOrderId.get(repairOrderId);
    }

    public void save(RepairOrder repairOrder){
        repairOrdersByRepairOrderId.put(repairOrder.getId(), repairOrder);
        List<RepairOrder> repairOrders = repairOrdersByTelephoneNumber.get(repairOrder.getCustomerDTO().getTelephoneNumber());
        if (repairOrders == null){
            repairOrders = new ArrayList<>();
            repairOrdersByTelephoneNumber.put(repairOrder.getCustomerDTO().getTelephoneNumber(), repairOrders);
        }
        boolean addNew = true;

        for (int i = 0; i < repairOrders.size(); i++){
            if (repairOrders.get(i).getId().equals(repairOrder.getId())){
                repairOrders.set(i, repairOrder);
                addNew = false;
            }
        }
        if (addNew){
            repairOrders.add(repairOrder);
        }
    }
}
