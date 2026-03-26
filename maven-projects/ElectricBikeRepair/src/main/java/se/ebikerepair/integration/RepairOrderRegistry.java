package se.ebikerepair.integration;

import java.util.HashMap;
import java.util.Map;

import se.ebikerepair.model.RepairOrder;

public class RepairOrderRegistry{
    private Map repairOrderMap;

    public RepairOrderRegistry(){
        repairOrderMap = new HashMap<String, RepairOrder>();
    }

    public void save(RepairOrder repairOrder){
        repairOrderMap.put(repairOrder.getId(), repairOrder);
    }
}
