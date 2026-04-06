package se.ebikerepair.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.ebikerepair.integration.BikeDTO;
import se.ebikerepair.integration.CustomerDTO;
import se.ebikerepair.integration.RegistryCreator;
import se.ebikerepair.integration.ProblemDTO;
import se.ebikerepair.integration.RepairOrderDTO;
import se.ebikerepair.model.RepairOrderState;
import se.ebikerepair.integration.Printer;

import static org.junit.jupiter.api.Assertions.*;

class ReceptionistControllerTest {
    private ReceptionistController controller;

    @BeforeEach
    void setUp() {
        RegistryCreator registryCreator = new RegistryCreator();
        controller = new ReceptionistController(registryCreator, new Printer());
    }

    @Test
    void testSearchExistingCustomer() {
        CustomerDTO customer = controller.searchCustomer("0707654321");
        assertNotNull(customer);
        assertEquals("Astrid Johansson", customer.name());
    }

    @Test
    void testSearchNonExistingCustomer() {
        assertThrows(IllegalArgumentException.class, () -> controller.searchCustomer("0700000000"));
    }

    @Test
    void testSearchInvalidPhoneNumber() {
        assertThrows(IllegalArgumentException.class, () -> controller.searchCustomer("abc"));
    }

    @Test
    void testCreateRepairOrder() {
        CustomerDTO customer = controller.searchCustomer("0707654321");
        BikeDTO bike = customer.bikes().get(0);
        String id = controller.createRepairOrder("0707654321", new ProblemDTO("Broken chain", bike));
        assertNotNull(id);
    }

    @Test
    void testCreateRepairOrderCustomerNotFound() {
        assertThrows(IllegalArgumentException.class, () ->
                controller.createRepairOrder("0700000000", new ProblemDTO("Issue", null)));
    }

    @Test
    void testAcceptRepairOrder() {
        CustomerDTO customer = controller.searchCustomer("0707654321");
        BikeDTO bike = customer.bikes().get(0);
        String id = controller.createRepairOrder("0707654321", new ProblemDTO("Broken chain", bike));

        controller.acceptRepairOrder(id);

        RepairOrderDTO dto = controller.findRepairOrder("0707654321");
        assertEquals(RepairOrderState.Accepted, dto.repairOrderState());
    }

    @Test
    void testAcceptOrderNotFound() {
        assertThrows(IllegalStateException.class, () -> controller.acceptRepairOrder("nonexistent-id"));
    }

    @Test
    void testRejectRepairOrder() {
        CustomerDTO customer = controller.searchCustomer("0707654321");
        BikeDTO bike = customer.bikes().get(0);
        String id = controller.createRepairOrder("0707654321", new ProblemDTO("Broken chain", bike));

        controller.rejectRepairOrder(id);

        RepairOrderDTO dto = controller.findRepairOrder("0707654321");
        assertEquals(RepairOrderState.Rejected, dto.repairOrderState());
    }

    @Test
    void testRejectOrderNotFound() {
        assertThrows(IllegalStateException.class, () -> controller.rejectRepairOrder("nonexistent-id"));
    }

    @Test
    void testSearchCustomerWithSpaces() {
        CustomerDTO customer = controller.searchCustomer("070-765 43 21");
        assertNotNull(customer);
        assertEquals("Astrid Johansson", customer.name());
    }

    @Test
    void testCreateAndFindRepairOrder() {
        CustomerDTO customer = controller.searchCustomer("0707654321");
        BikeDTO bike = customer.bikes().get(0);
        controller.createRepairOrder("0707654321", new ProblemDTO("Broken chain", bike));

        RepairOrderDTO dto = controller.findRepairOrder("0707654321");
        assertNotNull(dto);
        assertEquals("Astrid Johansson", dto.customerDTO().name());
        assertEquals(RepairOrderState.NewlyCreated, dto.repairOrderState());
    }
}
