package se.ebikerepair.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import se.ebikerepair.integration.BikeDTO;
import se.ebikerepair.integration.CustomerDTO;
import se.ebikerepair.model.ProblemDTO;
import se.ebikerepair.model.RepairOrder;
import se.ebikerepair.model.RepairOrderDTO;
import se.ebikerepair.model.RepairOrderState;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RepairOrderTest {

    @Test
    void testBasic() {
        List<BikeDTO> bikes = new ArrayList<>();
        BikeDTO brokenBike = new BikeDTO("Brand", "Model", "SerialNumber");
        bikes.add(brokenBike);
        CustomerDTO customer = new CustomerDTO("Alice", "0721312125", "alice@example.com", bikes);
        ProblemDTO problem = new ProblemDTO("Flat tire", brokenBike);

        RepairOrder order = new RepairOrder(customer, problem);

        assertEquals(customer, order.getCustomerDTO());
        // assertEquals(brokenBike, order.getBikeDTO());
        assertEquals(problem, order.getProblemDTO());

        assertNotNull(order.getCreatedDate());
        assertNotNull(order.getTotalCost());
        assertNotNull(order.getId());

        assertEquals(RepairOrderState.NewlyCreated, order.getRepairOrderState());
        assertNull(order.getEstimatedCompleteDate());
    }
    
    @Test
    void testToDTO() {
        List<BikeDTO> bikes = new ArrayList<>();
        BikeDTO brokenBike = new BikeDTO("Brand", "Model", "SerialNumber");
        bikes.add(brokenBike);
        CustomerDTO customer = new CustomerDTO("Bob", "0722382175", "bob@example.com", bikes);
        ProblemDTO problem = new ProblemDTO("Broken chain", brokenBike);

        RepairOrder order = new RepairOrder(customer, problem);

        RepairOrderDTO dto = order.toDTO();

        assertEquals(order.getCustomerDTO(), dto.customerDTO());
        assertEquals(order.getProblemDTO(), dto.problemDTO());
        assertEquals(order.getCreatedDate(), dto.createdDate());
        assertEquals(order.getTotalCost(), dto.totalCost());
        assertEquals(order.getRepairOrderState(), dto.repairOrderState());
        assertEquals(order.getId(), dto.id());
    }

    /*
    @Test
    void testNullCustomer() {
        BikeDTO bike = new BikeDTO("Brand", "Model", "SN");
        ProblemDTO problem = new ProblemDTO("Issue", bike);

        assertThrows(NullPointerException.class, () -> {
            new RepairOrder(null, problem);
        });
    }
         */
}
