package se.ebikerepair.controller;

import se.ebikerepair.integration.CustomerDTO;
import se.ebikerepair.integration.CustomerRegistry;
import se.ebikerepair.integration.RegistryCreator;
import se.ebikerepair.integration.RepairOrderRegistry;
import se.ebikerepair.util.TelephoneNumber;

public class ReceptionistController {
    private final CustomerRegistry customerRegistry;
    private final RepairOrderRegistry repairOrderRegistry;
    
    public ReceptionistController(RegistryCreator registryCreator){
        customerRegistry = registryCreator.getCustomerRegistry();
        repairOrderRegistry = registryCreator.getRepairOrderRegistry();
    }

    public CustomerDTO searchCustomer(String telephoneNumber){
        String phoneNumberE164 = new TelephoneNumber(telephoneNumber).toE164();
        CustomerDTO foundCustomer = customerRegistry.find(phoneNumberE164);
        return foundCustomer;
    }
}
