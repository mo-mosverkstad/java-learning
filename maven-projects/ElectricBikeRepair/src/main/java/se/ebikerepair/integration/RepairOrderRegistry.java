package se.ebikerepair.integration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import se.ebikerepair.model.RepairOrder;

/**
 * Registry for storing and retrieving repair orders, indexed by order ID and customer telephone number.
 */
public class RepairOrderRegistry{
    private Map<String, RepairOrder> repairOrdersByRepairOrderId;
    private Map<String, List<RepairOrder>> repairOrdersByTelephoneNumber;

    /**
     * Creates an empty repair order registry.
     */
    public RepairOrderRegistry(){
        repairOrdersByRepairOrderId = new HashMap<>();
        repairOrdersByTelephoneNumber = new HashMap<>();
    }

    /**
     * Finds all repair orders for a customer by telephone number.
     *
     * @param telephoneNumber the telephone number in E.164 format
     * @return list of repair orders, or empty list if none found
     */
    public List<RepairOrder> findRepairOrdersByTelephoneNumber(String telephoneNumber){
        return repairOrdersByTelephoneNumber.getOrDefault(telephoneNumber, Collections.emptyList());
    }

    /**
     * Finds a repair order by its unique ID.
     *
     * @param repairOrderId the repair order ID
     * @return the repair order, or null if not found
     */
    public RepairOrder findByRepairOrderId(String repairOrderId){
        return repairOrdersByRepairOrderId.get(repairOrderId);
    }

    /**
     * Saves or updates a repair order in the registry.
     *
     * @param repairOrder the repair order to save
     */
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
