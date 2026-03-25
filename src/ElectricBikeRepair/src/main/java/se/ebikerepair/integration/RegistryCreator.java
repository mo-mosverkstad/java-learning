package se.ebikerepair.integration;

public class RegistryCreator {
    private CustomerRegistry customerRegistry;
    private RepairOrderRegistry repairOrderRegistry;

    public RegistryCreator(){
        customerRegistry = new CustomerRegistry();
        repairOrderRegistry = new RepairOrderRegistry();
    }

    public CustomerRegistry getCustomerRegistry(){
        return customerRegistry;
    }

    public RepairOrderRegistry getRepairOrderRegistry(){
        return repairOrderRegistry;
    }
}
