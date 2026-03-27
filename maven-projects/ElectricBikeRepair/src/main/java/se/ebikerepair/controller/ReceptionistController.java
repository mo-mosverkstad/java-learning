package se.ebikerepair.controller;

import se.ebikerepair.integration.CustomerDTO;
import se.ebikerepair.integration.CustomerRegistry;
import se.ebikerepair.integration.RegistryCreator;
import se.ebikerepair.model.RepairOrder;
import se.ebikerepair.integration.RepairOrderRegistry;
import se.ebikerepair.util.TelephoneNumber;
import se.ebikerepair.model.ProblemDTO;
import se.ebikerepair.model.RepairOrderDTO;
import se.ebikerepair.printer.Printer;

public class ReceptionistController {
    private final CustomerRegistry customerRegistry;
    private final RepairOrderRegistry repairOrderRegistry;

    private CustomerDTO foundCustomer;
    private RepairOrder repairOrder;
    private Printer printer;
    
    public ReceptionistController(RegistryCreator registryCreator, Printer printer){
        customerRegistry = registryCreator.getCustomerRegistry();
        repairOrderRegistry = registryCreator.getRepairOrderRegistry();
        this.printer = printer;
    }

    public CustomerDTO searchCustomer(String telephoneNumber){
        String phoneNumberE164 = new TelephoneNumber(telephoneNumber).toE164();
        foundCustomer = customerRegistry.find(phoneNumberE164);
        return foundCustomer;
    }

    public String createRepairOrder(ProblemDTO problemDTO) throws IllegalStateException{
        if (foundCustomer == null) {
            throw new IllegalStateException("No customer found. Call searchCustomer() first.");
        }
        repairOrder = new RepairOrder(foundCustomer, problemDTO);
        repairOrderRegistry.save(repairOrder);
        return repairOrder.getId();
    }

    public RepairOrderDTO requestRepairOrder(String id) throws IllegalArgumentException{
        repairOrder = repairOrderRegistry.find(id);
        if (repairOrder == null) {
            throw new IllegalArgumentException("Repair order not found for id: " + id);
        }
        return repairOrder.toDTO();
    }
}
