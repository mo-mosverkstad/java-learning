package se.ebikerepair.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import se.ebikerepair.integration.BikeDTO;
import se.ebikerepair.integration.CustomerDTO;
import se.ebikerepair.integration.CustomerRegistry;
import se.ebikerepair.integration.RepairOrderRegistry;
import se.ebikerepair.model.ProblemDTO;
import se.ebikerepair.model.RepairOrder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RepairOrderRegistryTest {
    private RepairOrderRegistry registry;

    /*
    @BeforeEach
    void setUp() {
        registry = new RepairOrderRegistry();

        List<BikeDTO> bikes1 = new ArrayList<>();
        BikeDTO brokenBike1 = new BikeDTO("Brand", "Model", "SerialNumber");
        bikes1.add(brokenBike1);
        CustomerDTO customer1 = new CustomerDTO("Alice", "0721312125", "alice@example.com", bikes1);
        ProblemDTO problem1 = new ProblemDTO("Flat tire", brokenBike1);
        registry.save(new RepairOrder(customer1, problem1));

        List<BikeDTO> bikes2 = new ArrayList<>();
        BikeDTO brokenBike2 = new BikeDTO("Brand", "Model", "SerialNumber");
        bikes2.add(brokenBike2);
        CustomerDTO customer = new CustomerDTO("Bob", "0722382175", "bob@example.com", bikes2);
        ProblemDTO problem = new ProblemDTO("Broken chain", brokenBike2);
        registry.save(new RepairOrder(customer2, problem2));
    }

    @AfterEach
    void cleanUp(){
        registry = null;
    }

    @Test
    void testGetExistingRepairOrder(){
        RepairOrder repairOrder = registry.find();
    }
         */

}
