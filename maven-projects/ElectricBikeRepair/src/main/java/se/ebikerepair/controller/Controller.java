package se.ebikerepair.controller;

import se.ebikerepair.model.RepairOrder;
import se.ebikerepair.model.RepairOrderObserver;
import se.ebikerepair.integration.NoExistedRepairOrderException;
import se.ebikerepair.integration.RepairOrderDTO;
import se.ebikerepair.integration.RepairOrderRegistry;
import se.ebikerepair.util.InvalidTelephoneNumberException;
import se.ebikerepair.util.TelephoneNumber;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Base controller providing shared repair order lookup functionality.
 */
public class Controller {
    protected final RepairOrderRegistry repairOrderRegistry;
    protected final List<RepairOrderObserver> repairOrderObservers;

    /**
     * Creates a controller with the given repair order registry.
     *
     * @param repairOrderRegistry the registry for storing and retrieving repair orders
     */
    public Controller(RepairOrderRegistry repairOrderRegistry){
        this.repairOrderRegistry = repairOrderRegistry;
        this.repairOrderObservers = new ArrayList<>();
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
     * @throws NoExistedRepairOrderException if no repair orders exist for the customer or the order cannot be found
     */
    public RepairOrderDTO findRepairOrder(String telephoneNumber) throws InvalidTelephoneNumberException, NoExistedRepairOrderException{
        List<String> repairOrderIds = findRepairOrderIds(telephoneNumber);
        if (repairOrderIds.isEmpty()) {
            throw new NoExistedRepairOrderException("There is no repair order created for this customer.");
        }
        String id = repairOrderIds.get(0);
        RepairOrder repairOrder = repairOrderRegistry.findByRepairOrderId(id);
        return repairOrder.toDTO();
    }

    public void addRepairOrderObserver(RepairOrderObserver repairOrderObserver){
        repairOrderObservers.add(repairOrderObserver);
    }
    
}
