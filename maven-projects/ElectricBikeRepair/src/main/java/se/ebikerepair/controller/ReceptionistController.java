package se.ebikerepair.controller;

import se.ebikerepair.integration.CustomerDTO;
import se.ebikerepair.integration.CustomerRegistry;
import se.ebikerepair.integration.RegistryCreator;
import se.ebikerepair.model.RepairOrder;
import se.ebikerepair.integration.RepairOrderRegistry;
import se.ebikerepair.util.TelephoneNumber;
import se.ebikerepair.model.ProblemDTO;
import se.ebikerepair.model.RepairOrderDTO;
import se.ebikerepair.integration.Printer;

/**
 * Controller handling receptionist operations: customer search, repair order creation, acceptance and rejection.
 */
public class ReceptionistController extends Controller{
    private final CustomerRegistry customerRegistry;

    private final Printer printer;
    
    /**
     * Creates a receptionist controller with access to registries and a printer.
     *
     * @param registryCreator the creator providing access to data registries
     * @param printer the printer for printing accepted repair orders
     */
    public ReceptionistController(RegistryCreator registryCreator, Printer printer){
        super(registryCreator.getRepairOrderRegistry());
        customerRegistry = registryCreator.getCustomerRegistry();
        this.printer = printer;
    }

    /**
     * Searches for a customer by telephone number.
     *
     * @param telephoneNumber the customer's telephone number (any Swedish format)
     * @return the found customer DTO
     * @throws IllegalArgumentException if the phone number format is invalid or customer not found
     */
    public CustomerDTO searchCustomer(String telephoneNumber) throws IllegalArgumentException{
        String phoneNumberE164 = new TelephoneNumber(telephoneNumber).toE164();
        CustomerDTO foundCustomer = customerRegistry.find(phoneNumberE164);
        if (foundCustomer == null) {
            throw new IllegalArgumentException("No customer found for telephone number: " + telephoneNumber);
        }
        return foundCustomer;
    }

    /**
     * Creates a new repair order for the customer identified by telephone number.
     *
     * @param telephoneNumber the customer's telephone number (any Swedish format)
     * @param problemDTO the problem description with the broken bike
     * @return the generated repair order ID
     * @throws IllegalArgumentException if the customer is not found or phone format is invalid
     */
    public String createRepairOrder(String telephoneNumber, ProblemDTO problemDTO) throws IllegalArgumentException{
        CustomerDTO foundCustomer = searchCustomer(telephoneNumber);
        RepairOrder repairOrder = new RepairOrder(foundCustomer, problemDTO);
        repairOrderRegistry.save(repairOrder);
        return repairOrder.getId();
    }

    /**
     * Accepts a repair order, updates its state, saves it, and prints it.
     *
     * @param repairOrderId the ID of the repair order to accept
     * @throws IllegalStateException if the repair order is not found
     */
    public void acceptRepairOrder(String repairOrderId) throws IllegalStateException{
        RepairOrder repairOrder = repairOrderRegistry.findByRepairOrderId(repairOrderId);
        if (repairOrder == null) {
            throw new IllegalStateException("Repair order not found for id: " + repairOrderId);
        }
        repairOrder.accept();
        repairOrderRegistry.save(repairOrder);
        printer.print(repairOrder);
    }

    /**
     * Rejects a repair order and updates its state.
     *
     * @param repairOrderId the ID of the repair order to reject
     * @throws IllegalStateException if the repair order is not found
     */
    public void rejectRepairOrder(String repairOrderId) throws IllegalStateException{
        RepairOrder repairOrder = repairOrderRegistry.findByRepairOrderId(repairOrderId);
        if (repairOrder == null) {
            throw new IllegalStateException("Repair order not found for id: " + repairOrderId);
        }
        repairOrder.reject();
        repairOrderRegistry.save(repairOrder);
    }
}
