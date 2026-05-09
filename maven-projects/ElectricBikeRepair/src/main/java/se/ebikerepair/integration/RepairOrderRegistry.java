package se.ebikerepair.integration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

import se.ebikerepair.model.RepairOrder;

/**
 * Registry for storing and retrieving repair orders, indexed by order ID.
 */
public class RepairOrderRegistry{
    private final Map<String, RepairOrder> repairOrders;

    /**
     * Creates an empty repair order registry.
     */
    public RepairOrderRegistry(){
        repairOrders = new HashMap<>();
    }

    /**
     * Finds all repair orders for a customer by telephone number.
     *
     * @param telephoneNumber the telephone number in E.164 format
     * @return list of repair order DTOs, or empty list if none found
     */
    public List<RepairOrderDTO> findRepairOrdersByTelephoneNumber(String telephoneNumber){
        return repairOrders.values().stream()
            .filter(order -> order.getCustomerDTO().telephoneNumber().equals(telephoneNumber))
            .map(RepairOrder::toDTO)
            .collect(Collectors.toList());
    }

    /**
     * Finds a repair order by its unique ID.
     *
     * @param repairOrderId the repair order ID
     * @return the repair order
     * @throws NoExistedRepairOrderException if no repair order exists for the given ID
     */
    public RepairOrder findByRepairOrderId(String repairOrderId) throws NoExistedRepairOrderException{
        RepairOrder repairOrder = repairOrders.get(repairOrderId);
        if (repairOrder == null) {
            throw new NoExistedRepairOrderException(repairOrderId);
        }
        return new RepairOrder(repairOrder);
    }

    /**
     * Saves or updates a repair order in the registry.
     *
     * @param repairOrder the repair order to save
     */
    public void save(RepairOrder repairOrder){
        repairOrders.put(repairOrder.getId(), repairOrder);
    }
}
