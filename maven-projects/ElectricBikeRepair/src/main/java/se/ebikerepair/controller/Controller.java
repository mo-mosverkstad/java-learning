package se.ebikerepair.controller;

import se.ebikerepair.model.RepairOrder;
import se.ebikerepair.integration.RepairOrderDTO;
import se.ebikerepair.integration.RepairOrderRegistry;
import se.ebikerepair.util.InvalidTelephoneNumberException;
import se.ebikerepair.util.TelephoneNumber;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Base controller providing shared repair order lookup functionality.
 */
public class Controller {
    protected final RepairOrderRegistry repairOrderRegistry;

    /**
     * Creates a controller with the given repair order registry.
     *
     * @param repairOrderRegistry the registry for storing and retrieving repair orders
     */
    public Controller(RepairOrderRegistry repairOrderRegistry){
        this.repairOrderRegistry = repairOrderRegistry;
    }

    private List<String> findRepairOrderIds(String telephoneNumber) throws InvalidTelephoneNumberException{
        String phoneNumberE164 = new TelephoneNumber(telephoneNumber).toE164();
        List<RepairOrder> repairOrders = repairOrderRegistry.findRepairOrdersByTelephoneNumber(phoneNumberE164);
        return repairOrders.stream()
                .sorted(Comparator.comparing(RepairOrder::getCreatedDate).reversed())
                .map(RepairOrder::getId)
                .collect(Collectors.toList());
    }

    /**
     * Finds the most recent repair order for the given telephone number.
     *
     * @param telephoneNumber the customer's telephone number (any Swedish format)
     * @return the most recent repair order as a DTO
     * @throws InvalidTelephoneNumberException if the phone number format is invalid
     * @throws IllegalArgumentException if no repair orders exist for the customer
     * @throws IllegalStateException if the repair order ID exists but the order cannot be found (data inconsistency)
     */
    public RepairOrderDTO findRepairOrder(String telephoneNumber) throws InvalidTelephoneNumberException, IllegalArgumentException, IllegalStateException{
        List<String> repairOrderIds = findRepairOrderIds(telephoneNumber);
        if (repairOrderIds.isEmpty()) {
            throw new IllegalArgumentException("There is no repair order created for this customer.");
        }
        String id = repairOrderIds.get(0);
        RepairOrder repairOrder = repairOrderRegistry.findByRepairOrderId(id);
        if (repairOrder == null) {
            throw new IllegalStateException("Repair order not found for id: " + id);
        }
        return repairOrder.toDTO();
    }
    
}
