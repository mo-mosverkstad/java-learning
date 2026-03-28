package se.ebikerepair.integration;

/**
 * Factory class that creates and provides access to all data registries.
 */
public class RegistryCreator {
    private final CustomerRegistry customerRegistry;
    private final RepairOrderRegistry repairOrderRegistry;

    /**
     * Creates all registries.
     */
    public RegistryCreator(){
        customerRegistry = new CustomerRegistry();
        repairOrderRegistry = new RepairOrderRegistry();
    }

    /** @return the customer registry */
    public CustomerRegistry getCustomerRegistry(){
        return customerRegistry;
    }

    /** @return the repair order registry */
    public RepairOrderRegistry getRepairOrderRegistry(){
        return repairOrderRegistry;
    }
}
