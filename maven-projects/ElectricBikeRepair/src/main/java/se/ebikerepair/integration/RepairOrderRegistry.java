package se.ebikerepair.integration;

import java.util.HashMap;
import java.util.Map;

import se.ebikerepair.model.RepairOrder;

public class RepairOrderRegistry{
    private Map<String, RepairOrder> repairOrderMap;

    public RepairOrderRegistry(){
        repairOrderMap = new HashMap<>();
    }

    public RepairOrder find(String id){
        return repairOrderMap.get(id);
    }

    public void save(RepairOrder repairOrder){
        repairOrderMap.put(repairOrder.getId(), repairOrder);
    }
}
