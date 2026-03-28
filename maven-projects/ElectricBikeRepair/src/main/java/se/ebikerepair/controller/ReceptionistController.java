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

public class ReceptionistController extends Controller{
    private final CustomerRegistry customerRegistry;

    // private CustomerDTO foundCustomer;
    private Printer printer;
    
    public ReceptionistController(RegistryCreator registryCreator, Printer printer){
        super(registryCreator.getRepairOrderRegistry());
        customerRegistry = registryCreator.getCustomerRegistry();
        this.printer = printer;
    }

    public CustomerDTO searchCustomer(String telephoneNumber) throws IllegalArgumentException{
        String phoneNumberE164 = new TelephoneNumber(telephoneNumber).toE164();
        CustomerDTO foundCustomer = customerRegistry.find(phoneNumberE164);
        if (foundCustomer == null) {
            throw new IllegalArgumentException("No customer found for telephone number: " + telephoneNumber);
        }
        return foundCustomer;
    }

    public String createRepairOrder(String telephoneNumber, ProblemDTO problemDTO) throws IllegalArgumentException{
        CustomerDTO foundCustomer = searchCustomer(telephoneNumber);
        RepairOrder repairOrder = new RepairOrder(foundCustomer, problemDTO);
        repairOrderRegistry.save(repairOrder);
        return repairOrder.getId();
    }

    public void acceptOrder(String repairOrderId){
        RepairOrder repairOrder = repairOrderRegistry.findByRepairOrderId(repairOrderId);
        if (repairOrder == null) {
            throw new IllegalStateException("Repair order not found for id: " + repairOrderId);
        }
        repairOrder.accept();
        repairOrderRegistry.save(repairOrder);
        printer.print(repairOrder);
    }

    public void rejectOrder(String repairOrderId){
        RepairOrder repairOrder = repairOrderRegistry.findByRepairOrderId(repairOrderId);
        if (repairOrder == null) {
            throw new IllegalStateException("Repair order not found for id: " + repairOrderId);
        }
        repairOrder.reject();
        repairOrderRegistry.save(repairOrder);
    }
}
